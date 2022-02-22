import './Button.scss'

interface ButtonProps<T, TResult> {
    type?: "button" | "submit" | "reset"
    currentPage?: string
    value?: string
    onClick?: (item: T) => TResult
}

export default function Button({type, value, onClick, currentPage}: ButtonProps<any, any>) {

    const pageClass: string = currentPage === value ? " highlight" : ""

    return (
        <button type={type} className={"button" + pageClass } onClick={onClick}>{value}</button>
    )
}