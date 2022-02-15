import moment from "moment";

interface FormatDateProps {
    date: Date
}

export default function FormatDate({date}: FormatDateProps) {

    return (
        <>
            {moment(date).format('DD.MM.YYYY')}
        </>
    )

}