import './InputBox.scss'

interface TextBoxProps<T, TResult> {
    type?: string
    value?: string | number
    placeholder?: string
    id?: string
    step?: number
    onChange?: (item: T) => TResult
    max?: number
    min?: number
}

export default function InputBox({type, value, placeholder, id, onChange, step, max, min}: TextBoxProps<any, any>) {

    return (
        <input type={type || "text"}
               id={id}
               placeholder={placeholder}
               className={"textBox"}
               onChange={onChange}
               value={value}
               step={step}
               min={min}
               max={max}
        />
    )
}