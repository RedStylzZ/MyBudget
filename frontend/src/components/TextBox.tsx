import './TextBox.scss'

interface TextBoxProps<T, TResult> {
    type?: string
    value?: string | number
    placeholder?: string
    id?: string
    step?: number
    onChange?: (item: T) => TResult
}

export default function TextBox({type, value, placeholder, id, onChange, step}: TextBoxProps<any, any>) {

    return (
        <input type={type || "text"}
               id={id}
               placeholder={placeholder}
               className={"textBox"}
               onChange={onChange}
               value={value}
               step={step}
        />
    )
}