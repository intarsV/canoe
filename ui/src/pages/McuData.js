import React, {useContext, useEffect, useState} from "react";
import {getList, getListWithParams, processErrorMessage} from "../common/Utils";
import ReactTable from "react-table";
import {Col, Row} from "reactstrap";
import Api from "../common/Api";
import {RaceContext} from "../common/RaceContext";

const McuData = () => {
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});
    const {raceEventData, setRaceData} = useContext(RaceContext);
    const [mcuData, setMcuData] = useState([]);

    useEffect(() => {
            setInfoMessage({type: '', msg: ''})
            const eventSource = new EventSource('http://localhost:8080/api/v1/mcuData/emitter');
            eventSource.addEventListener("mcuData", (event) => {
                setMcuData(mcuData => [...mcuData, JSON.parse(event.data)]);
            });
            eventSource.onopen = e => console.log('open');
            eventSource.onerror = e => console.log(e);

            getList('/mcuData?isDone=false', setMcuData, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    const acceptRecord = (data) => {
        if (raceEventData.eventName !== 0 && raceEventData.subEvent !== 0) {
            data.done = true;
            data.eventId = raceEventData.eventName;
            data.subEventId = raceEventData.subEvent;
            data.teamMode = raceEventData.teamMode;
            Api.put('/mcuData/' + data.id, data)
                .then(response => {
                    let filteredArray = mcuData.filter(item => item.id !== data.id);
                    setMcuData(filteredArray);
                    const searchParams = {
                        eventId: raceEventData.eventName,
                        subEventId: raceEventData.subEvent,
                        teamMode: raceEventData.teamMode,
                        done: false,
                        disabled: false
                    }
                    getListWithParams('/race', searchParams, setRaceData, setInfoMessage);
                })
                .catch((error) => {
                    processErrorMessage(error, setInfoMessage);
                })
        } else {
            setInfoMessage({type: 'error', msg: "Need to set event un subEvent!"});
        }
    };


    const renderEditable = (cellInfo) => {
        return (
            <div
                contentEditable
                suppressContentEditableWarning
                onBlur={e => {
                    const data = [...mcuData];
                    if (!isNaN(parseInt(e.target.innerHTML))) {
                        data[cellInfo.index][cellInfo.column.id] = parseInt(e.target.innerHTML);
                    }
                    e.target.innerHTML = mcuData[cellInfo.index][cellInfo.column.id];
                    setMcuData(data);
                }}
            >{mcuData[cellInfo.index][cellInfo.column.id]}</div>
        );
    }

    return (
        <div className="card">
            <Col>
                <Row className="row-format">
                    <ReactTable
                        minRows={5} noDataText={'No data found'} showPagination={false}
                        data={mcuData.filter(e => e.unitId === 1)}
                        className={mcuData.length < 5 ? '-striped -highlight table-format'
                            : '-striped -highlight table-format-large'}
                        columns={[
                            {
                                className: "columnAlignCenter",
                                id: 'bib',
                                Header: "bib",
                                accessor: 'bib',
                                Cell: renderEditable
                            },
                            {
                                className: "columnAlignCenter",
                                id: 'timeStamp',
                                Header: "timeStamp",
                                accessor: 'timeStamp',
                            },
                            {
                                className: "columnAlignCenter",
                                id: 'accepted',
                                Header: "accept",
                                Cell: Cell => (
                                    <input type="checkbox"
                                           onClick={() => acceptRecord(Cell.original)}/>)
                            }
                        ]}
                    />
                    <ReactTable
                        minRows={5} noDataText={'No data found'} showPagination={false}
                        data={mcuData.filter(e => e.unitId === 2)}
                        className={mcuData.length < 5 ? '-striped -highlight table-format'
                            : '-striped -highlight table-format-large'}
                        columns={[
                            {
                                className: "columnAlignCenter",
                                id: 'bib',
                                Header: "bib",
                                accessor: 'bib',
                            },
                            {
                                className: "columnAlignCenter",
                                id: 'timeStamp',
                                Header: "timeStamp",
                                accessor: 'timeStamp',
                            },
                            {
                                className: "columnAlignCenter",
                                id: 'accepted',
                                Header: "accepted",
                                accessor: 'accepted',
                                Cell: Cell => (
                                    <input type="checkbox"
                                           onClick={() => acceptRecord(Cell.original)}/>)
                            }
                        ]}
                    />
                </Row>
            </Col>
            <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
        </div>
    )
};

export default McuData