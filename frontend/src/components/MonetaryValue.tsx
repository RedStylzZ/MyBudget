export interface IMonetaryValueProps {
    amount: number | undefined
}

export const toCurrency = (sum: number) => sum.toLocaleString('de-DE', {style: 'currency', currency: 'EUR'})

export default function MonetaryValue(props: IMonetaryValueProps) {
    const {amount} = props

    if (amount === undefined) return null
    return <>{toCurrency(amount!)}</>
}
