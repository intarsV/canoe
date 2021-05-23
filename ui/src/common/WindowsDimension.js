import {useEffect, useState} from "react";

const WindowsDimension = () => {
    const [windowDimensions, setWindowDimensions] = useState(
        {width: window.innerWidth, height: window.innerHeight}
    );

    const getWindowDimensions = () => {
        const {innerWidth: width, innerHeight: height} = window;
        return {
            width,
            height
        };
    }

    useEffect(() => {
        const handleResize = () => {
            setWindowDimensions(getWindowDimensions());
        }
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    return windowDimensions;
}

export default WindowsDimension;