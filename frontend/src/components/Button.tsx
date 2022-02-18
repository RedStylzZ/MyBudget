import './Button.scss'

interface ButtonProps<T, TResult> {
    value?: string
    onClick?: (item: T) => TResult
    submit?: boolean
}

export default function Button({value, onClick, submit}: ButtonProps<any, any>) {

    if (submit !== undefined) {
        return (
            <button type={"submit"} className={"button"}>{value}</button>
        )
    }

    return (
        <button className={"button"} onClick={onClick}>{value}</button>
    )
}