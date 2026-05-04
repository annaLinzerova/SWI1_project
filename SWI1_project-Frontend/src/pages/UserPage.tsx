import React, { useEffect, useState } from 'react'
import './UserPage.css'

interface Item {
    id: string | number;
    name: string;
    description: string;
    type: 'CHARACTER' | 'DLC' | 'SKIN';
}

const UserPage = () => {
    const [boughtItems, setBoughtItems] = useState<Item[]>([])
    const [isLoading, setIsLoading] = useState(false)
    const username = localStorage.getItem('username')

    const fetchUserProfile = async () => {
        setIsLoading(true)
        try {
            if (!username) return

            let items: Item[] = []

            // 1. Backend Data
            try {
                const response = await fetch(`http://localhost:8080/api/orders/user/${username}`)
                if (response.ok) {
                    const data = await response.json()
                    items = data.map((order: any, idx: number) => ({
                        id: order.id || `api-${idx}`,
                        name: order.itemName || order.characterName || order.dlcName || order.skinName,
                        description: order.itemDescription || "Purchased via Store",
                        type: (order.itemType || (order.characterName ? 'CHARACTER' : order.dlcName ? 'DLC' : 'SKIN')).toUpperCase() as Item['type']
                    }))
                }
            } catch (e) {}

            // 2. Local Storage Data
            const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
            local.forEach((localItem: any, idx: number) => {
                let name = "";
                let type: Item['type'] = 'CHARACTER';

                if (typeof localItem === 'string') {
                    name = localItem;
                    type = 'CHARACTER';
                } else {
                    name = localItem.name;
                    type = (localItem.type || 'CHARACTER').toUpperCase() as Item['type'];
                }

                if (name && !items.find(i => i.name === name)) {
                    items.push({
                        id: `local-${idx}`,
                        name: name,
                        description: "Purchased in Demo Mode",
                        type: type
                    })
                }
            })

            setBoughtItems(items)
        } catch (e) { console.error("Profile error", e) }
        finally { setIsLoading(false) }
    }

    const handleReset = () => {
        if (window.confirm("Clear all simulated purchases and start fresh?")) {
            localStorage.removeItem(`owned_${username}`)
            setBoughtItems([])
            window.dispatchEvent(new Event('storage'))
        }
    }

    useEffect(() => { 
        void fetchUserProfile() 
        window.addEventListener('storage', fetchUserProfile)
        return () => window.removeEventListener('storage', fetchUserProfile)
    }, [username])

    const renderTable = (type: Item['type'], title: string) => {
        const filtered = boughtItems.filter(i => i.type === type)
        return (
            <div className="profile-section">
                <h2>{title}</h2>
                {filtered.length === 0 ? <p className="empty-message">None owned yet.</p> : (
                    <table className="profile-table">
                        <tbody>
                            {filtered.map((item, idx) => (
                                <tr key={idx}>
                                    <td>{item.name}</td>
                                    <td className="owned-badge">Owned</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>
        )
    }

    if (isLoading && boughtItems.length === 0) {
        return (
            <div className="page profile-container">
                <div className="loading-container">
                    <div className="loading-spinner"></div>
                    <p>Fetching your collection...</p>
                </div>
            </div>
        )
    }

    return (
        <div className="page profile-container">
            <header className="profile-header">
                <h1>Player Profile</h1>
                <p>Welcome back, <span style={{color: '#4CAF50', fontWeight: 'bold'}}>{username}</span></p>
            </header>
            
            <div className="profile-controls">
                <button className="reset-btn" onClick={handleReset}>
                    Reset Collection
                </button>
            </div>

            <div className="profile-grid">
                {renderTable('CHARACTER', "Characters")}
                {renderTable('DLC', "DLCs")}
                {renderTable('SKIN', "Skins")}
            </div>
        </div>
    )
}

export default UserPage
