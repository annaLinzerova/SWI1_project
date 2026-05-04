import './LoginPage.css'
import React, { useState } from 'react'
import { Link, useNavigate } from "react-router-dom"

const LoginPage = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const navigate = useNavigate()

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()
        setError('')
        setIsLoading(true)

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ 
                    usernameOrEmail: username, 
                    password: password 
                }),
            })

            if (!response.ok) {
                let errorMessage = 'Invalid username or password'
                try {
                    const errorData = await response.json()
                    if (errorData.message) errorMessage = errorData.message
                    else if (errorData.error) errorMessage = errorData.error
                    else if (typeof errorData === 'string') errorMessage = errorData
                } catch (e) {
                    if (response.status === 404) {
                        errorMessage = 'API endpoint not found. Please verify the login URL.'
                    }
                }
                throw new Error(errorMessage)
            }
            
            // Set basic auth state in localStorage
            localStorage.setItem('isAuthenticated', 'true')
            localStorage.setItem('username', username)

            // Initialize coins for the user if they don't have any
            const userCoinsKey = `user_coins_${username}`
            if (localStorage.getItem(userCoinsKey) === null) {
                localStorage.setItem(userCoinsKey, '200') // Starting with 200 coins
            }
            
            // Dispatch a custom event to notify the Layout component that login happened
            window.dispatchEvent(new Event('auth-change'))
            
            // Navigate to home page on success
            navigate('/')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Login failed')
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <div className="login-container">
            <section className="velkyObdelnik">
                <h1>Login Page</h1>

                <form onSubmit={handleLogin}>
                    <p>
                        <label className="login" htmlFor="username">username / email: </label>
                        <input 
                            id="username" 
                            className="policko" 
                            name="username" 
                            type="text" 
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </p>
                    
                    <p>
                        <label className="login" htmlFor="password">password: </label>
                        <input 
                            id="password" 
                            className="policko" 
                            name="password" 
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </p>
                    
                    {error && <p className="error" style={{color: '#ff6b6b'}}>{error}</p>}

                    <button id="loginButton" className="loginButton" type="submit" disabled={isLoading}>
                        {isLoading ? 'Logging in...' : 'Login'}
                    </button>
                </form>

                <p>
                    If you are not registered yet, please click <Link to="/register" className="registerlink">here</Link>
                </p>
            </section>
        </div>
    )
}

export default LoginPage
