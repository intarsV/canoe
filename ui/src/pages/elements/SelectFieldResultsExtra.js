import React, {useContext} from "react";
import {ResultsContext} from "../../common/ResultsContext";
import {text_regex_number} from "../../common/Constants";

const SelectFieldResultsExtra = ({id, label, list, required, register, errors}) => {

    const {resultData, setResultData} = useContext(ResultsContext);

    return (
        <div className="span">
            <label className="marginLeftRight7" htmlFor={id}>{label}:</label>
            <select name={id}
                    className={errors[id] ? "input-field-error-state" : "input-field"}
                    ref={register({required: (required) === true, pattern: text_regex_number})}
                    onChange={(event) => {
                        if (id === "eventName") {
                            setResultData({
                                ...resultData,
                                [id]: parseInt(event.target[event.target.selectedIndex].getAttribute('data-id'))
                            })
                        } else {
                            setResultData({
                                ...resultData,
                                [id]: event.target[event.target.selectedIndex].value
                            })
                        }
                    }}>
                <option value={''}/>
                {list.map((item) =>
                    <option key={item.id} data-id={item.id}>{item[id]}</option>)
                }
            </select>
            <br/>
            {errors[id]?.type === "required" &&
            <span className="error-text">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="error-text">Invalid value!</span>}
        </div>
    )
};

export default SelectFieldResultsExtra