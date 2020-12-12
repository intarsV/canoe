import React from "react";

const SelectFieldTeam = ({id, label, pattern, list, valueField, register, errors}) => {

    return (
        <div className="row row-format">
            <label className="label" htmlFor={id}>{label}:</label>
            <select name={id}
                    className={errors[id] ? "input-field-error-state" : "input-field"}
                    ref={register({required: true, pattern: pattern})}>
                <option value={''}/>
                {list.map((item) =>
                    <option key={item.id}
                            value={item[id]}>{item[valueField]}</option>)
                }
            </select>
            {errors[id]?.type === "required" &&
            <span className="col-3 error-text">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="col-3 error-text">Invalid value!</span>}
        </div>
    )
}

export default SelectFieldTeam