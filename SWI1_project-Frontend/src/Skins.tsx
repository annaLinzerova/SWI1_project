import { useEffect, useState } from 'react'
import React from "react"

interface Skin {
  skinName: string;
  skinDescription: string;
  price?: number; 
}

interface OwnedItem {
    name: string;
    type: string;
}

function Skins() {
    const [skins, setSkins] = useState<Skin[]>([])
    const [ownedItemNames, setOwnedItemNames] = useState<string[]>([])
    const [isLoading, setIsLoading] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')
    const [successMessage, setSuccessMessage] = useState('')
    const [purchasing, setPurchasing] = useState<string | null>(null)
    const [userCoins, setUserCoins] = useState(0)
    
    const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true'
    const username = localStorage.getItem('username')

    const fetchUserCoins = () => {
        if (isAuthenticated && username) {
            setUserCoins(parseInt(localStorage.getItem(`user_coins_${username}`) || '0', 10))
        }
    }

    const fetchData = async () => {
        setIsLoading(true)
        setErrorMessage('')
        try {
            const response = await fetch('http://localhost:8080/api/skins')
            if (response.ok) setSkins(await response.json())

            if (isAuthenticated && username) {
                fetchUserCoins()
                let names: string[] = []
                try {
                    const res = await fetch(`http://localhost:8080/api/orders/user/${username}`)
                    if (res.ok) {
                        const data = await res.json()
                        names = data.map((i: any) => i.itemName || i.skinName || i.name)
                    }
                } catch (e) {}

                const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
                const localNames = local.map((i: any) => typeof i === 'string' ? i : i.name)
                setOwnedItemNames(Array.from(new Set([...names, ...localNames])))
            }
        } catch (e) { setErrorMessage('Failed to load skins') }
        finally { setIsLoading(false) }
    }

    const handleBuy = async (skin: Skin) => {
        if (!isAuthenticated) return setErrorMessage("Log in first")
        
        const price = skin.price || 10
        if (userCoins < price) {
            setErrorMessage("Not enough coins!")
            setTimeout(() => setErrorMessage(''), 3000)
            return
        }

        setPurchasing(skin.skinName)
        try {
            await fetch('http://localhost:8080/api/orders', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, itemName: skin.skinName, itemType: 'SKIN' })
            })
            saveLocally(skin.skinName, 'SKIN', price)
        } catch (e) {
            saveLocally(skin.skinName, 'SKIN', price)
        } finally { setPurchasing(null) }
    }

    const saveLocally = (name: string, type: string, price: number) => {
        const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
        if (!local.find((i: any) => (typeof i === 'string' ? i : i.name) === name)) {
            // Deduct coins
            const newBalance = userCoins - price
            localStorage.setItem(`user_coins_${username}`, newBalance.toString())
            setUserCoins(newBalance)

            local.push({ name, type })
            localStorage.setItem(`owned_${username}`, JSON.stringify(local))
            setOwnedItemNames(prev => [...prev, name])
            window.dispatchEvent(new Event('storage')) // Notify navbar and other components
            setSuccessMessage(`Successfully acquired ${name}!`)
            setTimeout(() => setSuccessMessage(''), 3000)
        }
    }

    useEffect(() => { void fetchData() }, [isAuthenticated, username])
    
    return (
        <div className="page">
            <header className="page-header">
                <h1>Skin Shop</h1>
                <p>Customize your survivors with unique skins</p>
            </header>

            <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '30px', minHeight: '40px' }}>
                {errorMessage && <span className="error">{errorMessage}</span>}
                {successMessage && <span className="success">{successMessage}</span>}
            </div>

            {isLoading ? <div className="loading-spinner" style={{margin: '0 auto'}}></div> : (
                <div className="shop-grid">
                    {skins.map((skin, i) => {
                        const isOwned = ownedItemNames.includes(skin.skinName)
                        const displayPrice = skin.price || 10; 
                        const canAfford = userCoins >= displayPrice;
                        return (
                            <div key={i} className="item-card">
                                <h3>{skin.skinName}</h3>
                                <p>{skin.skinDescription}</p>
                                <div style={{ marginTop: 'auto' }}>
                                    <div style={{ 
                                        fontSize: '1.5rem', 
                                        color: '#FFD700', 
                                        fontWeight: 'bold', 
                                        marginBottom: '15px',
                                        textAlign: 'center',
                                        textShadow: '0 0 10px rgba(255, 215, 0, 0.3)'
                                    }}>
                                        {isOwned ? '---' : `${displayPrice} 🪙`}
                                    </div>
                                    <button 
                                        className="buy-button" 
                                        style={{ width: '100%', opacity: (!isOwned && !canAfford) ? 0.5 : 1 }}
                                        onClick={() => handleBuy(skin)} 
                                        disabled={isOwned || purchasing === skin.skinName || (!isOwned && !canAfford)}
                                    >
                                        {isOwned ? 'Owned' : (purchasing === skin.skinName ? '...' : (canAfford ? 'Purchase' : 'Poor player'))}
                                    </button>
                                </div>
                            </div>
                        )
                    })}
                </div>
            )}
        </div>
    )
}
export default Skins
