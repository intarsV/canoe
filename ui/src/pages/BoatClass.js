import React, {useEffect, useState} from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {getList, processErrorMessage, setFieldValues} from "../common/Utils";
import {char_3_upper_char} from "../common/Constants";
import Api from "../common/Api";
import InputField from "./elements/InputField";
import {useForm} from "react-hook-form";

const BoatClass = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});
    const [boatClass, setBoatClass] = useState([]);

    useEffect(() => {
            getList('/boatClass', setBoatClass, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    const addBoatClass = data => {
        setInfoMessage({type: '', msg: ''});
        Api.post('/boatClass', data)
            .then(response => {
                setFieldValues({'id': '', 'boatClass': ''}, setValue);
                setBoatClass([...boatClass, response.data]);
                setInfoMessage({type: 'info', msg: "Boat class added successfully!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    return (
        <div className="col-md-6 col-sm-12 col-lg-3 top">
            <div className="marginLeftRight text-right">
                <form onSubmit={handleSubmit(addBoatClass)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                    <InputField id={'boatClass'} label={'Boat class'} pattern={char_3_upper_char}
                                register={register} errors={errors}/>
                    <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
                    <button className="button" type="submit">Add boat class</button>
                </form>
            </div>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={boatClass}
                className={boatClass.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                columns={[
                    {
                        id: 'boatClass',
                        Header: "boatClass",
                        accessor: 'boatClass',
                    }
                ]}
            />
        </div>
    )
};

export default BoatClass;