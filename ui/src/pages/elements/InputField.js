import React from "react";

const InputField = ({id, label, pattern, register, errors}) => {

    return (
        <div className="row row-format">
            <label className="label" htmlFor={id}>{label}:</label>
            <input name={id} type="text" autoComplete='off'
                   className={errors[id] ? "input-field-error-state" : "input-field"}
                   ref={register({required: true, pattern: pattern})}/>
            {errors[id]?.type === "required" &&
            <span className="col-3 error-text">Required field!</span>}
            {errors[id]?.type === "pattern" &&
            <span className="col-3 error-text">Invalid value!</span>}
        </div>
    )
};

export default InputField;