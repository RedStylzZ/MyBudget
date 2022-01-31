import RecentItem from "./RecentItem";
import './Recent.scss'

export default function Recent() {

    return (
        <div className={"recentOut"}>
            <h1>Resents</h1>
            <RecentItem />
        </div>
    )
}