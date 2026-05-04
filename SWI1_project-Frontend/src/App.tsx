import { useEffect, useState } from 'react'
import React from "react"

interface Character {
  characterName: string;
  characterDescription: string;

}

function App() {
  const [characters, setCharacters] = useState<Character[]>([])
  const [ownedItemNames, setOwnedItemNames] = useState<string[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')
  const [purchasing, setPurchasing] = useState<string | null>(null)
  
  const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true'
  const username = localStorage.getItem('username')

  const fetchData = async () => {
    setIsLoading(true)
    try {
      const charResponse = await fetch('http://localhost:8080/api/characters')
      if (charResponse.ok) setCharacters(await charResponse.json())

      if (isAuthenticated && username) {
        let names: string[] = []
        try {
          const res = await fetch(`http://localhost:8080/api/orders/user/${username}`)
          if (res.ok) {
            const data = await res.json()
            names = data.map((i: any) => i.itemName || i.characterName || i.name)
          }
        } catch (e) {}
        const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
        const localNames = local.map((i: any) => typeof i === 'string' ? i : i.name)
        setOwnedItemNames(Array.from(new Set([...names, ...localNames])))
      }
    } catch (e) { setErrorMessage("Connection error") }
    finally { setIsLoading(false) }
  }

  const handleBuy = async (char: Character) => {
    if (!isAuthenticated) return setErrorMessage("Log in first")
    setPurchasing(char.characterName)
    try {
        await fetch('http://localhost:8080/api/orders', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, itemName: char.characterName, itemType: 'CHARACTER' })
        })
        saveLocally(char.characterName, 'CHARACTER')
    } catch (e) {
        saveLocally(char.characterName, 'CHARACTER')
    } finally { setPurchasing(null) }
  }

  const saveLocally = (name: string, type: string) => {
    const local = JSON.parse(localStorage.getItem(`owned_${username}`) || '[]')
    if (!local.find((i: any) => (typeof i === 'string' ? i : i.name) === name)) {
        local.push({ name, type })
        localStorage.setItem(`owned_${username}`, JSON.stringify(local))
        setOwnedItemNames(prev => [...prev, name])
        window.dispatchEvent(new Event('storage'))
        setSuccessMessage(`Successfully acquired ${name}!`)
        setTimeout(() => setSuccessMessage(''), 3000)
    }
  }

  useEffect(() => { void fetchData() }, [isAuthenticated, username])

  return (
    <div className="page">
      <header className="page-header">
        <h1>Characters Shop</h1>
        <p>Unlock new survivors for your team</p>
      </header>

      <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '30px', minHeight: '40px' }}>
          {errorMessage && <span className="error">{errorMessage}</span>}
          {successMessage && <span className="success">{successMessage}</span>}
      </div>

      {isLoading ? <div className="loading-spinner" style={{margin: '0 auto'}}></div> : (
        <div className="shop-grid">
          {characters.map((char, index) => {
            const isOwned = ownedItemNames.includes(char.characterName);
            return (
              <div key={index} className="item-card">
                <h3>{char.characterName}</h3>
                <p>{char.characterDescription}</p>
                <div style={{ marginTop: 'auto' }}>
                    {/* No price display for characters */}
                    <button 
                      className="buy-button" 
                      style={{ width: '100%' }}
                      onClick={() => handleBuy(char)} 
                      disabled={isOwned || purchasing === char.characterName}
                    >
                      {isOwned ? 'Owned' : (purchasing === char.characterName ? '...' : 'Unlock')}
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
export default App
