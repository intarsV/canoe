import React, {useContext} from "react";
import ReactTable from "react-table";
import Api from "../../common/Api";
import {processErrorMessage, processResponse} from "../../common/Utils";
import {RaceContext} from "../../common/RaceContext";

const RaceTable = ({setInfoMessage, editMode}) => {

    const {raceEventData, raceData, setRaceData} = useContext(RaceContext);

    const generateColumnList = () => {
        let columnList = [
            {
                show: false,
                Header: "#",
                accessor: "id"
            },
            {
                width: 30,
                className: "columnAlignCenter",
                id: 'bib',
                Header: "Bib",
                accessor: 'eventRegistry.bib',
            },
            {
                width: 60,
                className: "columnAlignCenter",
                id: 'startTime',
                Header: "Start",
                accessor: 'startTime',
            },
            {
                width: 60,
                className: "columnAlignCenter",
                id: 'finishTime',
                Header: "Finish",
                accessor: 'finishTime',
            },
        ];
        for (let i = 1; i < 25; i++) {
            generateCells(columnList, i)
        }
        columnList.push(
            {
                width: 50,
                className: "columnAlignCenter",
                id: 'dsqr',
                Header: "DSQR",
                accessor: 'dsqr',
                Cell: cellInfo => (
                    <input type="checkbox" defaultChecked={cellInfo.original.dsqr}
                           onClick={() => postChanges(cellInfo.original, 'dsqr')}/>)
            },
            {
                width: 50,
                maxWidth: 50,
                className: "columnAlignCenter",
                id: 'done',
                Header: "Done",
                accessor: 'done',
                Cell: cellInfo => (
                    <input type="checkbox" defaultChecked={cellInfo.original.done}
                           onClick={() => {
                               postChanges(cellInfo.original, 'done')
                           }}/>)
            });
        return columnList
    };

    const generateCells = (columnList, i) => {
        if (raceEventData.teamMode === true) {
            columnList.push({
                className: "columnAlignCenter",
                id: 'g' + i,
                Header: "G" + i,
                accessor: 'g' + i,
                width: 36,
                Cell: renderEditable
            })
        } else {
            columnList.push({
                className: "columnAlignCenter",
                id: 'g' + i,
                Header: "G" + i,
                accessor: 'g' + i,
                width: 36,
                Cell: cellInfo => (
                    <button className={'table-cell-button'}
                            onClick={() => changeValue(cellInfo)}>{cellInfo.value}</button>
                )
            })
        }
    }

    const changeValue = (cellInfo) => {
        let cell = cellInfo.column.id;
        switch (cellInfo.value) {
            case 0:
                cellInfo.original[cell] = 2;
                break;
            case 2:
                cellInfo.original[cell] = 50;
                break;
            case 50:
                cellInfo.original[cell] = 0;
                break;
            default:
                cellInfo.original[cell] = 0;
        }
        updateRow(cellInfo.original);
    };

    const renderEditable = (cellInfo) => {
        return (
            <div
                contentEditable
                suppressContentEditableWarning
                onBlur={e => {
                    const data = [...raceData];
                    if (!isNaN(parseInt(e.target.innerHTML))) {
                        data[cellInfo.index][cellInfo.column.id] = parseInt(e.target.innerHTML);
                    }
                    e.target.innerHTML = raceData[cellInfo.index][cellInfo.column.id];
                    setRaceData(data);
                }}
            >{raceData[cellInfo.index][cellInfo.column.id]}</div>
        );
    }

    const postChanges = (data, accessor) => {
        data[accessor] = !data[accessor];
        updateRow(data);
    };

    const updateRow = (data) => {
        Api.put('/race/' + data.id, data)
            .then(response => {
                if (!editMode) {
                    processResponse(response, raceData, setRaceData, 'done');
                }
                setInfoMessage({type: 'info', msg: "Saved!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    return (
        <ReactTable
            minRows={1} noDataText={'No data found'} showPagination={false} data={raceData}
            pageSize = {raceData.length} className={raceData.length < 10 ? '-striped -highlight table-format'
                : '-striped -highlight table-format-large'}
            columns={
                generateColumnList()
            }
        />
    )
}

export default RaceTable