import React from "react";

const SelectFieldExtra = ({id, label, pattern, list, setCurrentEvent, register, errors}) => {

    return (
        <div className="row row-format">
            <label className="label" htmlFor={id}>{label}:</label>
            <select name={id}
                    className={errors[id] ? "input-field-error-state" : "input-field"}
                    ref={register({required: true, pattern: pattern})}
                    onChange={(event) => setCurrentEvent(event.target.value)}>
                <option value={''}/>
                {list.map((item) =>
                    <option key={item.id}
                            value={item[id]}>{item[id]}</option>)
                }
            </select>
            {errors[id]?.type === "required" &&
            <span className="col-3 error-text">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="col-3 error-text">Invalid value!</span>}
        </div>
    )
};

export default SelectFieldExtra