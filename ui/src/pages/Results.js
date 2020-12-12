import React, {useContext, useEffect, useState} from "react";
import {getList, processErrorMessage} from "../common/Utils";
import SelectFieldResultsExtra from "./elements/SelectFieldResultsExtra";
import {ResultsContext} from "../common/ResultsContext";
import Api from "../common/Api";
import {useForm} from "react-hook-form";

const Results = () => {
    const {register, handleSubmit, errors} = useForm({mode: 'all'});
    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});
    const {resultData, setResultData} = useContext(ResultsContext);

    const [event, setEvent] = useState([]);
    const [subEvent, setSubEvent] = useState([]);
    const [boatClass, setBoatClass] = useState([]);
    const reportTypes = [{id: 1, reportType: "StartList"}, {id: 2, reportType: "HeatResult"},
        {id: 3, reportType: "GrandTotal"}, {id: 4, reportType: "GrandTotalGroups"}]

    useEffect(() => {
        getList('/events', setEvent, setInfoMessage);
        getList('/sub-events', setSubEvent, setInfoMessage);
        getList('/boatClass', setBoatClass, setInfoMessage)
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    useEffect(() => {
        if (resultData.eventName !== 0 && event.length > 0) {
            setResultData({
                ...resultData,
                eventFormat: event.find(e => e.id === resultData.eventName).eventFormat.id
            });
        }
        //eslint-disable-next-line react-hooks/exhaustive-deps
    }, [resultData.eventName]);

    const getResults = () => {
        setInfoMessage({type: '', msg: ''});
        Api.getWithParams('/results', resultData)
            .then(response => {
                const pdf = base64ToArrayBuffer(response.data.object);
                downloadPdfWithName(new Blob([pdf], {type: "application/pdf; charset=UTF-8"}), 'test')
            })
            .catch((error) => {
                processErrorMessage(error, setInfoMessage);
            })
    };

    const base64ToArrayBuffer = base64 => {
        const binaryString = window.atob(base64),
            binaryLen = binaryString.length,
            bytes = new Uint8Array(binaryLen);
        for (let i = 0; i < binaryLen; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }
        return bytes;
    }

    const downloadPdfWithName = (file, filename) => {
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(file);
        link.setAttribute('download', filename)
        document.body.appendChild(link);
        if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveOrOpenBlob(file, filename)
        } else {
            link.click();
        }
    }

    return (
        <div className="card">
            <form onSubmit={handleSubmit(getResults)} onChange={() => setInfoMessage({type: '', msg: ''})}>
                <SelectFieldResultsExtra id={'eventName'} label={'Event'} list={event}
                                         register={register} errors={errors}/>

                <SelectFieldResultsExtra id={'subEvent'} label={'SubEvent'}
                                         list={subEvent.filter(e => e.eventFormat.id === resultData.eventFormat)}
                                         required={false} register={register} errors={errors}/>

                <SelectFieldResultsExtra id={'boatClass'} label={'Boat class'} list={boatClass} required={false}
                                         register={register} errors={errors}/>
                <label className="label row-format">Team mode:</label>
                <input type="checkbox" name="teamMode"
                       onChange={() => {
                           setResultData({...resultData, teamMode: !resultData.teamMode});
                       }}
                />
                <SelectFieldResultsExtra id={'reportType'} label={'Report type'} list={reportTypes} register={register}
                                         errors={errors}/>
                <button className="button" type="submit">Results</button>
                <span className={infoMessage.type === 'error' ? "col-md error-text" : "col-md info-text"}>
                    {infoMessage.msg}
                </span>
            </form>
        </div>
    )
}

export default Results