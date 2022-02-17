import './Button.scss'

interface ButtonProps {
    value?: string
    onClick?: () => void
    submit?: boolean
}

export default function Button({value, onClick, submit}: ButtonProps) {

    if (submit !== undefined) {
        return (
            <button type={"submit"} className={"button"} onClick={() => !(onClick) || onClick() || null}>{value}</button>
        )
    }

    return (
        <button className={"button"} onClick={() => !(onClick) || onClick() || null}>{value}</button>
    )
}