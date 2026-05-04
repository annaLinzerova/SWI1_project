import './RegisterPage.css'
import React, { useState } from 'react'
import { Link, useNavigate } from "react-router-dom"

const RegisterPage = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [email, setEmail] = useState('')
    const [error, setError] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const navigate = useNavigate()

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault()
        setError('')
        
        if (password !== confirmPassword) {
            setError("Passwords don't match")
            return
        }

        setIsLoading(true)

        try {
            const response = await fetch('http://localhost:8080/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ 
                    username: username,
                    email: email,
                    password: password 
                }),
            })

            if (!response.ok) {
                let errorMessage = 'Registration failed.'
                try {
                    const errorData = await response.json()
                    if (errorData.message) errorMessage = errorData.message
                    else if (errorData.error) errorMessage = errorData.error
                    else if (typeof errorData === 'string') errorMessage = errorData
                } catch (e) {
                    if (response.status === 404) {
                        errorMessage = 'API endpoint not found. Please verify the register URL.'
                    }
                }
                throw new Error(errorMessage)
            }

            // Navigate to login page on success
            navigate('/login')
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Registration failed')
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <section className="velkyObdelnik">
            <h1>Register Page</h1>

            <form onSubmit={handleRegister}>
                <p>
                    <label className="registr" htmlFor="username">Choose your username: </label>
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
                    <label className="registr" htmlFor="email">Email address: </label>
                    <input 
                        id="email" 
                        className="policko" 
                        name="email" 
                        type="email" 
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </p>

                <p>
                    <label className="registr" htmlFor="password">Choose a strong password: </label>
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

                <p>
                    <label className="registr" htmlFor="confirmPassword">Please repeat password: </label>
                    <input 
                        id="confirmPassword" 
                        className="policko" 
                        name="confirmPassword" 
                        type="password" 
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </p>

                {error && <p className="error" style={{color: '#ff6b6b'}}>{error}</p>}

                <button id="registerButton" className="registerButton" type="submit" disabled={isLoading}>
                    {isLoading ? 'Registering...' : 'Register'}
                </button>
            </form>

            <p>
                If you have registered yourself, please log in <Link to="/login" className="registerlink">here</Link>
            </p>
        </section>
    )
}

export default RegisterPage