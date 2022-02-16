import './Button.scss'

interface ButtonProps {
    description?: string
    onClick?: () => void
}

export default function Button({description, onClick}: ButtonProps) {

    return (
        <button className={"button"} onClick={() => !(onClick) || onClick() || null}>{description}</button>
    )
}