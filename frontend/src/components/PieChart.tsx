import {ResponsivePie} from "@nivo/pie";
import {toCurrency} from "./MonetaryValue";
import {PieChartData} from "../models/PieChart";

interface PieChartProps {
    data: PieChartData[]
}

const MyResponsivePie = (data: PieChartData[]) => (
    <ResponsivePie
        data={data}
        id={"id"}
        valueFormat={value => toCurrency(value)}
        margin={{top: 10, right: 40, bottom: 80, left: 40}}
        innerRadius={0.5}
        startAngle={0}
        endAngle={360}
        fit={true}
        padAngle={0.7}
        cornerRadius={3}
        theme={{
            "fontSize": 18
        }}
        activeInnerRadiusOffset={5}
        activeOuterRadiusOffset={8}
        colors={{scheme: 'category10'}}
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
        enableArcLinkLabels={false}
        arcLinkLabelsDiagonalLength={20}
        arcLinkLabelsStraightLength={24}
        arcLinkLabelsOffset={0}
        arcLinkLabelsTextOffset={6}
        arcLinkLabel={"id"}
        arcLinkLabelsSkipAngle={10}
        arcLinkLabelsTextColor={{from: 'color', modifiers: []}}
        arcLinkLabelsThickness={2}
        arcLinkLabelsColor={{from: 'color'}}
        arcLabelsSkipAngle={10}
        arcLabelsTextColor={{
            from: 'color',
            modifiers: [
                [
                    'darker',
                    3
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
                            itemTextColor: '#656565'
                        }
                    }
                ]
            }
        ]}
    />
)


export default function PieChart(props: PieChartProps) {
    const {data} = props

    return <>{MyResponsivePie(data)}</>
}