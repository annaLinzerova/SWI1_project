import {StrictMode} from 'react'
// @ts-ignore
import {createRoot} from 'react-dom/client'
// @ts-ignore
import './index.css'
import App from './App.tsx'
import React from "react"

// @ts-ignore
createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <App/>
    </StrictMode>,
)
