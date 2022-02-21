import './Button.scss'

interface ButtonProps<T, TResult> {
    type?: "button" | "submit" | "reset"
    value?: string
    onClick?: (item: T) => TResult
}

export default function Button({type, value, onClick}: ButtonProps<any, any>) {

    return (
        <button type={type} className={"button"} onClick={onClick}>{value}</button>
    )
}