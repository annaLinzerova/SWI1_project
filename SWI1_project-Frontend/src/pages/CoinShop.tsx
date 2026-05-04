import React, { useState, useEffect } from 'react'
import './CoinShop.css'

const CoinShop = () => {
    const [userCoins, setUserCoins] = useState(0)
    const [successMessage, setSuccessMessage] = useState('')
    const username = localStorage.getItem('username')

    const fetchUserCoins = () => {
        if (username) {
            const coins = parseInt(localStorage.getItem(`user_coins_${username}`) || '0', 10)
            setUserCoins(coins)
        }
    }

    useEffect(() => {
        fetchUserCoins()
        window.addEventListener('storage', fetchUserCoins)
        return () => window.removeEventListener('storage', fetchUserCoins)
    }, [username])

    const handleBuyCoins = (amount: number) => {
        if (!username) return

        const currentCoins = parseInt(localStorage.getItem(`user_coins_${username}`) || '0', 10)
        const newTotal = currentCoins + amount
        
        localStorage.setItem(`user_coins_${username}`, newTotal.toString())
        setUserCoins(newTotal)
        
        // Notify other components (like Navbar)
        window.dispatchEvent(new Event('storage'))
        
        setSuccessMessage(`Successfully added ${amount} coins to your balance!`)
        setTimeout(() => setSuccessMessage(''), 3000)
    }

    const coinBundles = [
        { amount: 100, price: '0.99€', bonus: '' },
        { amount: 500, price: '4.49€', bonus: '+50 Bonus!' },
        { amount: 1000, price: '8.99€', bonus: '+150 Bonus!' },
        { amount: 2500, price: '19.99€', bonus: '+500 Bonus!' },
    ]

    return (
        <div className="page coin-shop-container">
            <header className="page-header">
                <h1>Gold Reserve</h1>
                <p>Top up your balance to unlock exclusive content</p>
            </header>

            <div className="current-balance-display">
                <span>Your Current Balance:</span>
                <span className="balance-amount">{userCoins} 🪙</span>
            </div>

            {successMessage && (
                <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
                    <span className="success">{successMessage}</span>
                </div>
            )}

            <div className="shop-grid">
                {coinBundles.map((bundle, idx) => (
                    <div key={idx} className="item-card coin-bundle-card">
                        <div className="coin-icon-large">🪙</div>
                        <h3>{bundle.amount} Gold</h3>
                        {bundle.bonus && <span className="bonus-tag">{bundle.bonus}</span>}
                        <div className="bundle-price">{bundle.price}</div>
                        <button 
                            className="buy-button" 
                            onClick={() => handleBuyCoins(bundle.amount + (bundle.amount === 500 ? 50 : bundle.amount === 1000 ? 150 : bundle.amount === 2500 ? 500 : 0))}
                        >
                            Buy Now
                        </button>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default CoinShop
