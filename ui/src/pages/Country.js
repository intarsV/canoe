import React, {useEffect, useState} from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {getList, processErrorMessage, setFieldValues} from "../common/Utils";
import {text_3_upper_char} from "../common/Constants";
import Api from "../common/Api";
import InputField from "./elements/InputField";
import {useForm} from "react-hook-form";

const Country = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [country, setCountry] = useState([]);

    useEffect(() => {
            getList('/countries', setCountry, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    const addCountry = data => {
        Api.post('/countries', data)
            .then(response => {
                setFieldValues({'id': '', 'country': ''}, setValue);
                setCountry([...country, response.data]);
                setInfoMessage({type: 'info', msg: "Country added successfully!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    return (
        <div>
            <form onSubmit={handleSubmit(addCountry)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <InputField id={'country'} label={'Country'} pattern={text_3_upper_char}
                            register={register} errors={errors} setInfoMessage={setInfoMessage}/>
                <button className="button" type="submit">Add country</button>
                <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={country}
                className={country.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                columns={[
                    {
                        className: "columnAlignCenter",
                        maxWidth: 80,
                        id: 'country',
                        Header: "Country",
                        accessor: 'country',
                    }
                ]}
            />
        </div>
    )
};

export default Country;