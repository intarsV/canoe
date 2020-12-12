import React, {useContext} from "react";
import {RaceContext} from "../../common/RaceContext";
import {text_regex_number} from "../../common/Constants";

const SelectFieldRaceExtra = ({id, label, list, register, errors}) => {
    const {raceEventData, setRaceEventData, selection, setSelection} = useContext(RaceContext);

    const getValue = () => {
        if (selection[id] !== '') {
            return selection[id]
        }
    }

    return (
        <div className="row row-format">
            <label className="label" htmlFor={id}>{label}:</label>
            <select name={id}
                    value={getValue()}
                    className={errors[id] ? "input-field-error-state" : "input-field"}
                    ref={register({required: true, pattern: text_regex_number})}
                    onChange={(event) => {
                        setRaceEventData({
                            ...raceEventData,
                            [id]: parseInt(event.target[event.target.selectedIndex].getAttribute('data-id'))
                        })
                        setSelection({...selection, [id]: event.target.value})
                    }
                    }>
                <option value={''}/>
                {list.map((item) =>
                    <option key={item.id} data-id={item.id}>{item[id]}</option>)
                }
            </select>
            {errors[id]?.type === "required" &&
            <span className="col-3 error-text">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="col-3 error-text">Invalid value!</span>}
        </div>
    )
};

export default SelectFieldRaceExtra