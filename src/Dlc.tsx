import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material'
import React from "react"

function Dlc() {
    const dlcData = [
        {
            dlc_id: 'John',
            dlc_name: 'test@example.com',
            dlc_description: 'John',
            id: '2709a361-52c1-4a37-8944-91e23348d0f1',
        },
    ]
    const columns = ['character_id', 'character_name', 'character_description', 'id']
    return (<div className="page">
        <header className="page-header">
            <h1>Dlc</h1>
        </header>
            <Table>
                <TableHead>
                    <TableRow>
                        {columns.map((column) => (
                            <TableCell key={column}>{column}</TableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {dlcData.map((dlc) => (
                        <TableRow key={dlc.id}>
                            {columns.map((column) => (
                                <TableCell key={`${dlc.id}-${column}`}>
                                    {String(dlc[column as keyof typeof dlc])}
                                </TableCell>
                            ))}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
    </div>)
}
export default Dlc
