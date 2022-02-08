
export interface IMonetaryValueProps {
    amount: number
}

export const toCurrency = (sum: number) => sum.toLocaleString('de-DE', {style: 'currency', currency: 'EUR'})

export default function MonetaryValue(props : IMonetaryValueProps) {
    const {amount} = props
    return <>{toCurrency(amount)}</>
}
