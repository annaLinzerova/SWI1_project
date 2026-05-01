import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material'
import React from "react"

function Order() {
    const orderData = [
        {
            order_id: 'John',
            order_date: 'test@example.com',
            order_description: 'John',
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
                {orderData.map((order) => (
                    <TableRow key={order.id}>
                        {columns.map((column) => (
                            <TableCell key={`${order.id}-${column}`}>
                                {String(order[column as keyof typeof order])}
                            </TableCell>
                        ))}
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    </div>)
}
export default Order