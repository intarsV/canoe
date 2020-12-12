import React, {useEffect, useState} from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {getList, processErrorMessage, processResponse, setFieldValues} from "../common/Utils";
import {number_regex, text_3_upper_char, text_regex} from "../common/Constants";
import Api from "../common/Api";
import InputField from "./elements/InputField";
import SelectField from "./elements/SelectField";
import {useForm} from "react-hook-form";

const Competitor = () => {

    const {register, handleSubmit, setValue, errors} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [competitor, setCompetitor] = useState([]);
    const [countryList, setCountryList] = useState([]);
    const [currentId, setCurrentId] = useState('');

    useEffect(() => {
            getList('/countries', setCountryList, setInfoMessage);
            getList('/competitors', setCompetitor, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    const addCompetitor = data => {
        setInfoMessage({type: '', msg: ''});
        currentId ?
            Api.put('/competitors/' + currentId, data)
                .then(response => {
                    processResponse(response, competitor, setCompetitor, 'disabled');
                    setFieldValues({
                        'id': '',
                        'competitorName': '',
                        'birthYear': '',
                        'club': '',
                        'country': ''
                    }, setValue);
                    setCurrentId('');
                    setInfoMessage({type: 'info', msg: "Competitor added successfully!"});
                })
                .catch((error) => {
                    processErrorMessage(error, setInfoMessage);
                })
            :
            Api.post('/competitors', data)
                .then(response => {
                    setFieldValues({
                        'id': '',
                        'competitorName': '',
                        'birthYear': '',
                        'club': '',
                        'country': ''
                    }, setValue);
                    setCompetitor([...competitor, response.data]);
                    setInfoMessage({type: 'info', msg: "Competitor added successfully!"});
                })
                .catch((error) => {
                    processErrorMessage(error, setInfoMessage);
                });
    };

    const setRowDataToFields = (data) => {
        setCurrentId(data.id);
        setFieldValues({
            competitorName: data.competitorName,
            birthYear: data.birthYear,
            club: data.club,
            country: data.country,
        }, setValue);
    };

    return (
        <div>
            <form onSubmit={handleSubmit(addCompetitor)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <InputField id={'competitorName'} label={'Name'} pattern={text_regex}
                            register={register} errors={errors}/>
                <InputField id={'birthYear'} label={'Year'} pattern={number_regex}
                            register={register} errors={errors}/>
                <InputField id={'club'} label={'Club'} pattern={text_regex}
                            register={register} errors={errors} setInfoMessage={setInfoMessage}/>
                <SelectField id={'country'} label={'Country'} list={countryList} pattern={text_3_upper_char}
                             register={register} errors={errors}/>
                {currentId &&
                <div className="row row-format">
                    <label htmlFor="disabled">remove : </label>
                    <input name="disabled" type="checkbox" autoComplete='off'
                           ref={register()}
                           onClick={() => {
                               setInfoMessage({type: '', msg: ''});
                           }}/>
                </div>
                }
                <button className="button" type="submit">Add competitor</button>
                <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={competitor}
                className={competitor.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                getTdProps={(state, rowInfo) => {
                    return {
                        onClick: (e, handleOriginal) => {
                            const rowData = rowInfo.original;
                            setRowDataToFields(rowData);
                        }
                    }
                }}
                columns={[
                    {
                        id: 'competitorName',
                        Header: "Name",
                        accessor: 'competitorName',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'birthYear',
                        Header: "Year",
                        accessor: 'birthYear',
                    },
                    {
                        id: 'club',
                        Header: "Club",
                        accessor: 'club',
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'country',
                        Header: "Country",
                        accessor: 'country',
                    },
                ]}
            />
        </div>
    )
};

export default Competitor