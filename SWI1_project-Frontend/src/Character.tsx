import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material'
import React from "react"

function Character() {
    const characterData = [
        {
            character_id: 'John',
            character_name: 'test@example.com',
            character_description: 'John',
            id: '2709a361-52c1-4a37-8944-91e23348d0f1',
        },
    ]
    const columns = ['character_id', 'character_name', 'character_description', 'id']
    return (<div className="page">
        <header className="page-header">
            <h1>Characters</h1>
        </header>
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        {columns.map((column) => (
                            <TableCell key={column}>{column}</TableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {characterData.map((character) => (
                        <TableRow key={character.id}>
                            {columns.map((column) => (
                                <TableCell key={`${character.id}-${column}`}>
                                    {String(character[column as keyof typeof character])}
                                </TableCell>
                            ))}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    </div>)
}
export default Character
