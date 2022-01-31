import Categories from "../components/Categories";
import Recent from "../components/Recent";
import './HomePage.scss'

export default function HomePage() {



    return (
        <div className={"homePage"}>
            <div className={"recentOuts"}>
                <Recent />
            </div>
            <div className={"homeCategories"}>
                <Categories />
            </div>
        </div>
    )
}