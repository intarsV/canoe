import React, {useEffect, useState} from "react";
import {getBibValue, getList, processErrorMessage, processResponse, setFieldValues} from "../common/Utils";
import {
    number_regex_free,
    text_regex,
    text_regex_groups,
    text_regex_number
} from "../common/Constants";
import ReactTable from "react-table";
import Api from "../common/Api";
import SelectField from "./elements/SelectField";
import InputField from "./elements/InputField";
import SelectFieldExtra from "./elements/SelectFieldExtra";
import {useForm} from "react-hook-form";
import SelectFieldTeam from "./elements/SelectFieldTeam";

const EventRegistry = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [eventRegistry, setEventRegistry] = useState([]);
    const [event, setEvent] = useState([]);
    const [ageGroup, setAgeGroup] = useState([]);
    const [boatClass, setBoatClass] = useState([]);
    const [competitor, setCompetitor] = useState([]);
    const [newEventRegistry, setNewEventRegistry] = useState({});
    const [raceMode, setRaceMode] = useState('single');

    const [currentEvent, setCurrentEvent] = useState({});
    const [reverse, setReverse] = useState(false);
    const [currentId, setCurrentId] = useState('');

    useEffect(() => {
            getList('/events', setEvent, setInfoMessage);
            getList('/groups', setAgeGroup, setInfoMessage);
            getList('/boatClass', setBoatClass, setInfoMessage);
            getList('/competitors', setCompetitor, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    useEffect(() => {
        if (Object.keys(currentEvent).length !== 0) {
            let findEvent = event.find(element => element.eventName === currentEvent);
            getList('/eventRegistry?eventId=' + findEvent.id + '&teamMode=' + (raceMode !== 'single'), setEventRegistry, setInfoMessage);
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentEvent, raceMode]);

    useEffect(() => {
        if (Object.keys(newEventRegistry).length !== 0) {
            currentId ? updateToList() : addToList();
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [newEventRegistry]);

    const updateToList = () => {
        Api.put('/eventRegistry/' + currentId, newEventRegistry)
            .then(response => {
                processResponse(response, eventRegistry, setEventRegistry, 'disabled');
                setFieldValues({
                    competitorName: '', competitorName2: '', competitorName3: '', bib: getBibValue(newEventRegistry.bib)
                }, setValue);
                setCurrentId('');
                setInfoMessage({type: 'info', msg: "Registered!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    const addToList = () => {
        Api.post('/eventRegistry', newEventRegistry)
            .then(response => {
                setFieldValues({
                    competitorName: '',
                    competitorName2: '',
                    competitorName3: '',
                    bib: getBibValue(reverse, newEventRegistry.bib)
                }, setValue);
                setEventRegistry([...eventRegistry, response.data]);
                setInfoMessage({type: 'info', msg: "Registered!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    const createNewEventRegistry = (data) => {
        let findEvent = event.find(element => element.eventName === data.eventName);
        let findGroup = ageGroup.find(element => element.ageGroup === data.ageGroup);
        let findBoatClass = boatClass.find(element => element.boatClass === data.boatClass);
        let findCompetitor = competitor.find(element => element.competitorName === data.competitorName);

        setNewEventRegistry({
            event: findEvent,
            competitor: findCompetitor,
            ageGroup: findGroup,
            boatClass: findBoatClass,
            teamMates: raceMode !== 'single' ? data.competitorName2 + ', ' + data.competitorName3 : '',
            bib: data.bib,
            teamMode: raceMode !== 'single',
            disabled: data.disabled
        });
    };

    const setRowDataToFields = (data) => {
        setCurrentId(data.id);
        setFieldValues({
            event: data.event.eventName,
            ageGroup: data.ageGroup.ageGroup,
            boatClass: data.boatClass.boatClass,
            competitorName: data.competitor.competitorName,
            bib: data.bib
        }, setValue);
        if (data.teamMode) {
            let values = data.teamMates.split(',');
            setFieldValues({
                competitorName2: values[0].trim(),
                competitorName3: values[1].trim(),
                bib: data.bib
            }, setValue);
        }
    };

    return (
        <div className="col-md-10 col-sm-12 col-lg-8 top">
            <div className="marginLeftRight text-right">
                <form onSubmit={handleSubmit(createNewEventRegistry)}
                      onChange={() => setInfoMessage({type: '', msg: ''})}>
                    <SelectFieldExtra id={'eventName'} label={'Event'} pattern={text_regex_number} list={event}
                                      setCurrentEvent={setCurrentEvent} register={register} errors={errors}/>
                    <SelectField id={'ageGroup'} label={'Group'} pattern={text_regex_groups} list={ageGroup}
                                 register={register} errors={errors}/>
                    <SelectField id={'boatClass'} label={'BoatClass'} pattern={text_regex_groups} list={boatClass}
                                 register={register} errors={errors}/>
                    <SelectField id={'competitorName'} label={'Competitor'} pattern={text_regex} list={competitor}
                                 register={register} errors={errors}/>
                    {raceMode === 'team' &&
                    <>
                        <SelectFieldTeam id={'competitorName2'} label={'Competitor 2'} pattern={text_regex}
                                         list={competitor}
                                         valueField={'competitorName'} register={register} errors={errors}/>

                        <SelectFieldTeam id={'competitorName3'} label={'Competitor 3'} pattern={text_regex}
                                         list={competitor}
                                         valueField={'competitorName'} register={register} errors={errors}/>
                    </>
                    }
                    <InputField id={'bib'} label={'Bib'} pattern={number_regex_free}
                                register={register} errors={errors}/>
                    <div className="text-right">
                        <label className="marginLeftRight">Race mode: </label>
                        <input type="radio" name="teamMode" className=" marginLeft"
                               checked={raceMode === 'single'}
                               onChange={() => {
                                   setInfoMessage({type: '', msg: ''});
                                   setRaceMode('single');
                               }}
                        />
                        <input type="radio" name="teamMode" className=" marginLeft"
                               checked={raceMode === 'team'}
                               onChange={() => {
                                   setInfoMessage({type: '', msg: ''});
                                   setRaceMode('team');
                               }}
                        />
                    </div>
                    <div className="text-right">
                        <label className="marginLeftRight" htmlFor="reverse">reverse : </label>
                        <input name="reverse" type="checkbox" autoComplete='off' className=" marginLeft"
                               onClick={() => {
                                   setInfoMessage({type: '', msg: ''});
                                   setReverse(true)
                               }}/>
                    </div>
                    {currentId &&
                    <div className="row row-format">
                        <label className="marginLeftRight" htmlFor="disabled">remove : </label>
                        <input name="disabled" type="checkbox" autoComplete='off'
                               ref={register()}
                               onClick={() => {
                                   setInfoMessage({type: '', msg: ''});
                                   setReverse(true)
                               }}/>
                    </div>
                    }
                    <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
                    <button className="button" type="submit">Register</button>
                </form>
            </div>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={eventRegistry}
                className="-striped -highlight table-format-large"
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
                        id: 'bib',
                        maxWidth: 30,
                        Header: "bib",
                        accessor: 'bib',
                    },
                    {
                        id: 'competitor',
                        Header: "Competitor",
                        accessor: 'competitor.competitorName',
                    },
                    {
                        show: raceMode !== 'single',
                        id: 'teamMates',
                        Header: "Team mates",
                        accessor: 'teamMates',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'boatClass',
                        Header: "Class",
                        accessor: 'boatClass.boatClass',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'group',
                        Header: "Group",
                        accessor: 'ageGroup.ageGroup',
                    }
                ]}
            />
        </div>
    )
};

export default EventRegistry