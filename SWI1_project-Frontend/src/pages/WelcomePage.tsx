import React from 'react'
import './WelcomePage.css'
import { Link } from 'react-router-dom'

const WelcomePage = () => {
    const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';

    return (
        <div className="page">
            <header className="page-header">
                <h1>AWAKE BY MIDNIGHT</h1>
                <p>Survive the Dark. Escape the Killer.</p>
            </header>

            <div className="welcome-content">
                <section className="info-section">
                    <h2>The Experience</h2>
                    <p>
                        <strong>Awake by Midnight</strong> is an intense, heart-pounding 4-player co-op horror game. 
                        Work together with your team to outsmart and escape a relentless killer stalking you from the shadows. 
                        Every second counts, every decision matters, and no one is safe until the clock strikes midnight.
                    </p>
                </section>

                <section className="info-section">
                    <h2>Official Market & Hub</h2>
                    <p>
                        This is the official platform for <strong>Awake by Midnight</strong> players. 
                        Our site is exclusively designed for:
                    </p>
                    <ul className="welcome-list">
                        <li><strong>Marketplace:</strong> Securely purchase new Characters, game-changing DLCs, and exclusive Skins.</li>
                        <li><strong>Transactions:</strong> Manage your purchases and view transaction history in one secure place.</li>
                        <li><strong>Player Profile:</strong> Log in to see your personal collection and showcase what you own.</li>
                    </ul>
                </section>
            </div>

            <div className="welcome-actions">
                {!isAuthenticated ? (
                    <>
                        <Link to="/login" className="welcome-btn primary">Login to Shop</Link>
                        <Link to="/register" className="welcome-btn secondary">Join the Survivors</Link>
                    </>
                ) : (
                    <>
                        <Link to="/characters" className="welcome-btn primary">Enter the Shop</Link>
                        <Link to="/profile" className="welcome-btn secondary">View My Profile</Link>
                    </>
                )}
            </div>
        </div>
    )
}

export default WelcomePage
