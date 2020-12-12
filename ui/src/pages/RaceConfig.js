import React, {useEffect, useState} from "react";
import {getList, processErrorMessage, processResponse, setFieldValues} from "../common/Utils";
import SelectField from "./elements/SelectField";
import {
    number_regex_free,
    text_regex_groups,
    text_regex_number
} from "../common/Constants";
import Api from "../common/Api";
import ReactTable from "react-table";
import InputField from "./elements/InputField";
import SelectFieldExtra from "./elements/SelectFieldExtra";
import {useForm} from "react-hook-form";

const RaceConfig = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [event, setEvent] = useState([]);
    const [boatClass, setBoatClass] = useState([]);
    const [currentEvent, setCurrentEvent] = useState({});
    const [raceConfig, setRaceConfig] = useState([]);
    const [currentId, setCurrentId] = useState(0);

    useEffect(() => {
            getList('/events', setEvent, setInfoMessage);
            getList('/boatClass', setBoatClass, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    useEffect(() => {
        if (Object.keys(currentEvent).length !== 0) {
            let query = {eventId: event.find(e => e.eventName === currentEvent).id}
            // console.log(query)
            Api.getWithParams('/race-config', query)
                .then(response => {
                    setRaceConfig(response.data);
                })
                .catch((error) => {
                    processErrorMessage(error, setInfoMessage);
                })
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentEvent])

    const addRaceConfig = (data) => {
        console.log(data)
        let findEvent = event.find(element => element.eventName === data.eventName);
        console.log(findEvent)
        let findBoatClass = boatClass.find(element => element.boatClass === data.boatClass);
        let newData = {
            event: findEvent,
            boatClass: findBoatClass,
            heat1: data.heat1,
            heat2: data.heat2,
            semiFinal: data.semiFinal
        }
        currentId ? putRaceConfig(newData) : postRaceConfig(newData);
    }

    const postRaceConfig = (data) => {
        Api.post('/race-config', data)
            .then(response => {
                setFieldValues({boatClass: '', heat1: '', heat2: '', semiFinal: ''}, setValue);
                setRaceConfig([...raceConfig, response.data]);
                setInfoMessage({type: 'info', msg: "Registered!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    }

    const putRaceConfig = (data) => {
        Api.put('/race-config/' + currentId, data)
            .then(response => {
                processResponse(response, raceConfig, setRaceConfig, 'disabled');
                setFieldValues({boatClass: '', heat1: '', heat2: '', semiFinal: ''}, setValue);
                setCurrentId(0);
                setInfoMessage({type: 'info', msg: "Registered!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    const setRowDataToFields = (data) => {
        setCurrentId(data.id);
        setFieldValues({
            event: data.event.eventName,
            boatClass: data.boatClass.boatClass,
            heat1: data.heat1,
            heat2: data.heat2,
            semiFinal: data.semiFinal,
        }, setValue);
    };

    return (
        <div>
            <form onSubmit={handleSubmit(addRaceConfig)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <SelectFieldExtra id={'eventName'} label={'Event'} pattern={text_regex_number} list={event}
                                  setCurrentEvent={setCurrentEvent} register={register} errors={errors}/>
                <SelectField id={'boatClass'} label={'Boat class'} list={boatClass} pattern={text_regex_groups}
                             register={register} errors={errors}/>
                <InputField id={'heat1'} label={'Heat 1'} pattern={number_regex_free}
                            register={register} errors={errors}/>
                <InputField id={'heat2'} label={'Heat 2'} pattern={number_regex_free}
                            register={register} errors={errors}/>
                <InputField id={'semiFinal'} label={'SemiFinal'} pattern={number_regex_free}
                            register={register} errors={errors}/>
                <button className="button" type="submit">Add configuration</button>
                <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={raceConfig}
                className={event.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                getTdProps={(state, rowInfo) => {
                    return {
                        onClick: () => {
                            const rowData = rowInfo.original;
                            setRowDataToFields(rowData);
                        }
                    }
                }}
                columns={[
                    {
                        className: "columnAlignCenter",
                        id: 'boatClass',
                        Header: "BoatClass",
                        accessor: 'boatClass.boatClass',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'heat1',
                        Header: "Heat1",
                        accessor: 'heat1',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'heat2',
                        Header: "Heat2",
                        accessor: 'heat2',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'semiFinal',
                        Header: "SemiFinal",
                        accessor: 'semiFinal',
                    },
                ]}
            />
        </div>
    )
}

export default RaceConfig