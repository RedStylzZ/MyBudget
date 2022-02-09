import {ResponsivePie} from "@nivo/pie";
import {Category} from "../models/Category";
import {toCurrency} from "./MonetaryValue";
import { animated } from '@react-spring/web'

interface PieChartProps {
    categories: Category[]
}

interface PieChartData {
    id: string,
    label: string,
    value: number,
    color?: string
}

const MyResponsivePie = (data: PieChartData[]) => (
    <ResponsivePie
        data={data}
        id={"id"}
        valueFormat={value => toCurrency(value)}
        margin={{ top: 40, right: 80, bottom: 80, left: 80 }}
        innerRadius={0.5}
        startAngle={0}
        endAngle={360}
        fit={true}
        padAngle={0.7}
        cornerRadius={3}
        activeInnerRadiusOffset={5}
        activeOuterRadiusOffset={8}
        colors={{ scheme: 'category10' }}
        borderWidth={1}
        borderColor={{
            from: 'color',
            modifiers: [
                [
                    'darker',
                    0.2
                ]
            ]
        }}
        enableArcLabels={true}
        arcLabel={"formattedValue"}
        arcLabelsRadiusOffset={0.5}
        enableArcLinkLabels={true}
        arcLinkLabelsDiagonalLength={20}
        arcLinkLabelsStraightLength={24}
        arcLinkLabelsOffset={0}
        arcLinkLabelsTextOffset={6}
        arcLabelsComponent={({ datum, label, style }) => (
                        <animated.g transform={style.transform} style={{ pointerEvents: 'none' }}>
                            <circle fill={style.textColor} cy={6} r={15} />
                            <circle fill="#ffffff" stroke={datum.color} strokeWidth={2} r={16} />
                            <text
                                textAnchor="middle"
                                dominantBaseline="central"
                                fill={style.textColor}
                                style={{
                                    fontSize: 10,
                                    fontWeight: 800,
                                }}
                            >
                                {label}
                            </text>
                        </animated.g>
                    )}
        arcLinkLabel={"id"}
        arcLinkLabelsSkipAngle={10}
        arcLinkLabelsTextColor={{ from: 'color', modifiers: [] }}
        arcLinkLabelsThickness={2}
        arcLinkLabelsColor={{ from: 'color' }}
        arcLabelsSkipAngle={10}
        arcLabelsTextColor={{
            from: 'color',
            modifiers: [
                [
                    'darker',
                    2
                ]
            ]
        }}
        defs={[
            {
                id: 'dots',
                type: 'patternDots',
                background: 'inherit',
                color: 'rgba(255, 255, 255, 0.3)',
                size: 4,
                padding: 1,
                stagger: true
            },
            {
                id: 'lines',
                type: 'patternLines',
                background: 'inherit',
                color: 'rgba(255, 255, 255, 0.3)',
                rotation: -45,
                lineWidth: 6,
                spacing: 10
            }
        ]}
        legends={[
            {
                anchor: 'bottom',
                direction: 'row',
                justify: false,
                translateX: 0,
                translateY: 56,
                itemsSpacing: 0,
                itemWidth: 100,
                itemHeight: 18,
                itemTextColor: '#999',
                itemDirection: 'left-to-right',
                itemOpacity: 1,
                symbolSize: 18,
                symbolShape: 'circle',
                effects: [
                    {
                        on: 'hover',
                        style: {
                            itemTextColor: '#000'
                        }
                    }
                ]
            }
        ]}
    />
)

const mapCategoriesToValues = (categories: Category[]) => {
    const data: PieChartData[] = categories.map(category => {
        return {
            id: category.categoryName,
            label: category.categoryName,
            value: category.paymentSum,
        }
    })
    return data
}

export default function PieChart(props: PieChartProps) {
    const {categories} = props
    return <>{MyResponsivePie(mapCategoriesToValues(categories))}</>
}