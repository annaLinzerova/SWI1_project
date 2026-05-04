import { StrictMode, useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route, Link, useNavigate, Navigate } from 'react-router-dom'
// @ts-ignore
import './style.css'
import App from './App.tsx'
import React from "react"
import LoginPage from './pages/LoginPage.tsx'
import RegisterPage from './pages/RegisterPage.tsx'
import UserPage from './pages/UserPage.tsx'
import WelcomePage from './pages/WelcomePage.tsx'
import CoinShop from './pages/CoinShop.tsx'
import Dlc from './Dlc.tsx'
import Skins from './Skins.tsx'


function ProtectedRoute({ children }: { children: React.ReactNode }) {
    const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true'
    
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />
    }

    return <>{children}</>
}

function Layout({ children }: { children: React.ReactNode }) {
    const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem('isAuthenticated') === 'true')
    const [userCoins, setUserCoins] = useState(0)
    const navigate = useNavigate()
    const username = localStorage.getItem('username')

    const fetchUserCoins = () => {
        if (isAuthenticated && username) {
            const coins = parseInt(localStorage.getItem(`user_coins_${username}`) || '0', 10)
            setUserCoins(coins)
        } else {
            setUserCoins(0)
        }
    }

    const checkAuth = () => {
        setIsAuthenticated(localStorage.getItem('isAuthenticated') === 'true')
        fetchUserCoins()
    }

    useEffect(() => {
        fetchUserCoins()
        window.addEventListener('auth-change', checkAuth)
        window.addEventListener('storage', checkAuth)

        return () => {
            window.removeEventListener('auth-change', checkAuth)
            window.removeEventListener('storage', checkAuth)
        }
    }, [isAuthenticated, username])

    const handleLogout = () => {
        localStorage.removeItem('isAuthenticated')
        localStorage.removeItem('username')
        setIsAuthenticated(false)
        setUserCoins(0)
        window.dispatchEvent(new Event('auth-change'))
        navigate('/login')
    }

    return (
        <div>
            <nav style={{ 
                padding: '1rem 2rem', 
                background: '#003022', 
                marginBottom: '1rem', 
                display: 'flex', 
                gap: '1rem',
                borderBottom: '2px solid #36160D',
                justifyContent: 'space-between',
                alignItems: 'center'
            }}>
                <div style={{ display: 'flex', gap: '1.5rem' }}>
                    <Link to="/" style={{ color: '#4CAF50', textDecoration: 'none', fontWeight: 'bold' }}>Home</Link>
                    <Link to="/characters" style={{ color: '#4CAF50', textDecoration: 'none', fontWeight: 'bold' }}>Characters</Link>
                    <Link to="/dlc" style={{ color: '#4CAF50', textDecoration: 'none', fontWeight: 'bold' }}>DLCs</Link>
                    <Link to="/skins" style={{ color: '#4CAF50', textDecoration: 'none', fontWeight: 'bold' }}>Skins</Link>
                </div>
                
                <div style={{ display: 'flex', gap: '1.5rem', alignItems: 'center' }}>
                    {isAuthenticated ? (
                        <>
                            <Link to="/buy-coins" style={{ 
                                background: 'rgba(255, 215, 0, 0.1)',
                                padding: '5px 12px',
                                borderRadius: '20px',
                                border: '1px solid rgba(255, 215, 0, 0.3)',
                                color: '#FFD700', 
                                textDecoration: 'none',
                                fontWeight: 'bold', 
                                fontSize: '1.1rem' 
                            }}>
                                {userCoins} 🪙
                            </Link>
                            <Link to="/profile" style={{ color: '#f0f0f0', textDecoration: 'none', fontWeight: 'bold' }}>Profile</Link>
                            <button 
                                onClick={handleLogout} 
                                style={{ 
                                    background: 'none', 
                                    border: 'none', 
                                    color: '#ff6b6b', 
                                    cursor: 'pointer', 
                                    fontWeight: 'bold',
                                    fontSize: '16px',
                                    padding: 0
                                }}
                            >
                                Logout
                            </button>
                        </>
                    ) : (
                        <>
                            <Link to="/login" style={{ color: '#f0f0f0', textDecoration: 'none', fontWeight: 'bold' }}>Login</Link>
                            <Link to="/register" style={{ color: '#f0f0f0', textDecoration: 'none', fontWeight: 'bold' }}>Register</Link>
                        </>
                    )}
                </div>
            </nav>
            <main>
                {children}
            </main>
        </div>
    )
}

// @ts-ignore
createRoot(document.getElementById('app')!).render(
    <StrictMode>
        <BrowserRouter>
            <Layout>
                <Routes>
                    <Route path="/" element={<WelcomePage />} />
                    <Route path="/characters" element={<App />} />
                    <Route path="/dlc" element={<Dlc />} />
                    <Route path="/skins" element={<Skins />} />
                    <Route path="/buy-coins" element={
                        <ProtectedRoute>
                            <CoinShop />
                        </ProtectedRoute>
                    } />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/profile" element={
                        <ProtectedRoute>
                            <UserPage />
                        </ProtectedRoute>
                    } />
                </Routes>
            </Layout>
        </BrowserRouter>
    </StrictMode>,
)
