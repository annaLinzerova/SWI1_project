import { useEffect, useState } from 'react'
import React from "react"

interface DlcItem {
  dlcName: string;
  dlcDescription: string;
  price?: number; 
}

interface OwnedItem {
    name: string;
    type: string;
}

function Dlc() {
    const [dlcs, setDlcs] = useState<DlcItem[]>([])
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

            const response = await fetch('http://localhost:8080/api/dlcs')
            if (response.ok) setDlcs(await response.json())


            if (isAuthenticated && username) {
                fetchUserCoins()
                let names: string[] = []
                try {
                    const res = await fetch(`http://localhost:8080/api/orders/user/${username}`)
                    if (res.ok) {
                        const data = await res.json()
                        names = data.map((i: any) => i.itemName || i.dlcName || i.name)
                    }
                } catch (e) {}

                const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
                const localNames = local.map((i: any) => typeof i === 'string' ? i : i.name)
                setOwnedItemNames(Array.from(new Set([...names, ...localNames])))
            }
        } catch (e) { setErrorMessage('Failed to load DLCs') }
        finally { setIsLoading(false) }
    }

    const handleBuy = async (dlc: DlcItem) => {
        if (!isAuthenticated) return setErrorMessage("Log in first")
        
        const price = dlc.price || 50
        if (userCoins < price) {
            setErrorMessage("Not enough coins!")
            setTimeout(() => setErrorMessage(''), 3000)
            return
        }

        setPurchasing(dlc.dlcName)
        try {
            await fetch('http://localhost:8080/api/orders', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, itemName: dlc.dlcName, itemType: 'DLC' })
            })
            saveLocally(dlc.dlcName, 'DLC', price)
        } catch (e) {
            saveLocally(dlc.dlcName, 'DLC', price)
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
                <h1>DLC Shop</h1>
                <p>Expand your horizon with new content</p>
            </header>

            <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '30px', minHeight: '40px' }}>
                {errorMessage && <span className="error">{errorMessage}</span>}
                {successMessage && <span className="success">{successMessage}</span>}
            </div>

            {isLoading ? <div className="loading-spinner" style={{margin: '0 auto'}}></div> : (
                <div className="shop-grid">
                    {dlcs.map((dlc, i) => {
                        const isOwned = ownedItemNames.includes(dlc.dlcName)
                        const displayPrice = dlc.price || 50; 
                        const canAfford = userCoins >= displayPrice;
                        return (
                            <div key={i} className="item-card">
                                <h3>{dlc.dlcName}</h3>
                                <p>{dlc.dlcDescription}</p>
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
                                        onClick={() => handleBuy(dlc)} 
                                        disabled={isOwned || purchasing === dlc.dlcName || (!isOwned && !canAfford)}
                                    >
                                        {isOwned ? 'Owned' : (purchasing === dlc.dlcName ? '...' : (canAfford ? 'Purchase' : 'Poor player'))}
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
export default Dlc
