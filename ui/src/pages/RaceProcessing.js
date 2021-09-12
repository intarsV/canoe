import React, {useContext, useEffect, useState} from "react";
import {getList, getListWithParams} from "../common/Utils";
import {RaceContext} from "../common/RaceContext";
import SelectFieldRaceExtra from "./elements/SelectFieldRaceExtra";
import RaceTable from "./elements/RaceTable";
import {useForm} from "react-hook-form";
import McuData from "./McuData";
import {Col, Row} from "reactstrap";
import Results from "./Results";

const RaceProcessing = () => {
    const {register, errors} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({});
    const {raceEventData, setRaceEventData, setRaceData} = useContext(RaceContext);

    const [event, setEvent] = useState([]);
    const [subEvent, setSubEvent] = useState([]);
    const [editMode, setEditMode] = useState()

    useEffect(() => {
        getList('/events', setEvent, setInfoMessage);
        getList('/sub-events', setSubEvent, setInfoMessage);
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {
        if (raceEventData.eventName !== 0 && event.length > 0) {
            setRaceData([]);
            setRaceEventData({
                ...raceEventData,
                eventFormat: event.find(element => element.id === raceEventData.eventName).eventFormat.id
            })
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [raceEventData.eventName]);

    useEffect(() => {
        if (raceEventData.subEvent !== 0) {
            const searchParams = {
                eventId: raceEventData.eventName,
                subEventId: raceEventData.subEvent,
                teamMode: raceEventData.teamMode,
                done: editMode,
                disabled: false
            }
            getListWithParams('/race', searchParams, setRaceData, setInfoMessage);
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [raceEventData.subEvent, raceEventData.teamMode, editMode]);

    return (
        <div className="top" onChange={() => setInfoMessage({type: '', msg: ''})}>
            <Row>
                <Col className="text-right col-lg-3">
                    <SelectFieldRaceExtra id={'eventName'} label={'Event'} list={event} register={register}
                                          errors={errors}/>
                    <SelectFieldRaceExtra id={'subEvent'} label={'SubEvent'}
                                          list={subEvent.filter(e => e.eventFormat.id === raceEventData.eventFormat)}
                                          register={register} errors={errors}/>
                    <div>
                        <label className="marginLeftRight">Team mode:</label>
                        <input type="checkbox" name="teamMode" className=" marginLeft"
                               checked={raceEventData.teamMode}
                               onChange={() => {
                                   setRaceEventData({...raceEventData, teamMode: !raceEventData.teamMode});
                               }}
                        />
                    </div>
                    <div>
                        <label className="marginLeftRight">Edit mode:</label>
                        <input type="checkbox" name="editMode" className=" marginLeft"
                               checked={editMode}
                               onChange={() => {
                                   setEditMode(!editMode);
                               }}
                        />
                        <br/>
                        <span className={infoMessage.type === 'error' ? "col-md error-text" : "col-md info-text"}>
                                 {infoMessage.msg}
                        </span>
                    </div>
                </Col>
                <Col className="align-self-center" lg="4" md="4">
                    <McuData/>
                </Col>
                <Col className="align-self-center" lg="3" md="3">
                    <Results/>
                </Col>
            </Row>
            <RaceTable setInfoMessage={setInfoMessage} editMode={editMode}/>
        </div>
    )
}

export default RaceProcessing