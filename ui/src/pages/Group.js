import React, {useEffect, useState} from "react";
import ReactTable from "react-table";
import {text_regex_groups} from "../common/Constants";
import {getList, processErrorMessage, setFieldValues} from "../common/Utils";
import Api from "../common/Api";
import InputField from "./elements/InputField";
import {useForm} from "react-hook-form";

const Group = () => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    const [ageGroup, setAgeGroup] = useState([]);

    useEffect(() => {
            getList('/groups', setAgeGroup, setInfoMessage);
            //eslint-disable-next-line react-hooks/exhaustive-deps
        }, []
    );

    const addAgeGroup = data => {
        Api.post('/groups', data)
            .then(response => {
                setFieldValues({'id': '', 'ageGroup': ''}, setValue);
                setAgeGroup([...ageGroup, response.data]);
                setInfoMessage({type: 'info', msg: "Age group added successfully!"});
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    return (
        <div>
            <form onSubmit={handleSubmit(addAgeGroup)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <InputField id={'ageGroup'} label={'Age group'} pattern={text_regex_groups}
                            register={register} errors={errors}/>
                <button className="button" type="submit">Add age group</button>
                <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={ageGroup}
                className={ageGroup.length < 10 ? '-striped -highlight table-format'
                    : '-striped -highlight table-format-large'}
                columns={[
                    {
                        id: 'ageGroup',
                        Header: "Age group",
                        accessor: 'ageGroup',
                    }
                ]}
            />
        </div>
    )
};
export default Group