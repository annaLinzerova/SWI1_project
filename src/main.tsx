import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
// @ts-ignore
import './style.css'
import App from './App.tsx'
import React from "react"

// @ts-ignore
createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <App/>
    </StrictMode>,
)
