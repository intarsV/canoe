import React, {useEffect, useState} from "react";
import {text_regex_number} from "../common/Constants";
import ReactTable from "react-table";
import {getList, processErrorMessage, setFieldValues} from "../common/Utils";
import Api from "../common/Api";
import SelectField from "./elements/SelectField";
import InputField from "./elements/InputField";
import {useForm} from "react-hook-form";

const Event = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [event, setEvent] = useState([]);
    const [eventFormat, setEventFormat] = useState([]);
    const [newEvent, setNewEvent] = useState({});

    useEffect(() => {
            getList('/events', setEvent, setInfoMessage);
            getList('/eventFormats', setEventFormat, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    useEffect(() => {
        if (Object.keys(newEvent).length !== 0) {
            Api.post('/events', newEvent)
                .then(response => {
                    setFieldValues({'id': '', 'eventName': '', 'formatName': '', 'placeDate': ''}, setValue);
                    setEvent([...event, response.data]);
                    setInfoMessage({type: 'info', msg: "Country added successfully!"});
                })
                .catch((error) => {
                    processErrorMessage(error, setInfoMessage);
                })
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [newEvent]);

    const addEvent = (data) => {
        let findFormat = eventFormat.find(element => element.formatName === data.formatName);
        console.log((findFormat));
        setNewEvent({eventName: data.eventName, eventFormat: findFormat, placeDate: data.placeDate});
    };

    return (
        <div>
            <form onSubmit={handleSubmit(addEvent)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <InputField id={'eventName'} label={'Event'} pattern={text_regex_number}
                            register={register} errors={errors}/>
                <InputField id={'placeDate'} label={'Place and date'} pattern={text_regex_number}
                            register={register} errors={errors}/>
                <SelectField id={'formatName'} label={'Format'} list={eventFormat}
                             register={register} errors={errors}/>
                <button className="button" type="submit">Add event</button>
                <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={event}
                className={event.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                columns={[
                    {
                        id: 'eventName',
                        Header: "Event",
                        accessor: 'eventName',
                    },
                    {
                        id: 'eventFormat',
                        Header: "Format",
                        accessor: 'eventFormat.formatName',
                    },
                    {
                        id: 'placeDate',
                        Header: "Place",
                        accessor: 'placeDate',
                    },
                ]}
            />
        </div>
    )
};

export default Event