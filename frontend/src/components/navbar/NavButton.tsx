import './NavButton.scss'
import {ReactElement} from "react";

interface NavButtonProps<T, TResult> {
    icon?: ReactElement
    currentPage?: string
    value?: string
    onClick?: (item: T) => TResult
}

export default function NavButton({icon, value, onClick, currentPage}: NavButtonProps<any, any>) {

    const pageClass: string = currentPage === value ? " highlight" : ""

    return (
        <div className={"navButton" + pageClass} onClick={onClick}>
            {icon}
            <p>{value}</p>
        </div>
    )
}
