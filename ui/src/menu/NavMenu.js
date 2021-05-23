import React, {useState} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Dropdown, DropdownMenu, DropdownToggle} from "reactstrap";
import useWindowDimensions from "../common/WindowsDimension";

const NavMenu = () => {
    const {width} = useWindowDimensions();
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const toggle = () => setDropdownOpen(prevState => !prevState);

    const userMenu = [
        {key: 'Competitor', value: '/competitor'},
        {key: 'Event', value: '/event'},
        {key: 'Event Registry', value: '/event-registry'},
        {key: 'Race Processing', value: '/race-processing'},
        {key: 'RaceConfig', value: '/race-config'},
        {key: 'Config', value: '/config'}
    ];

    return (
        <>
            {width > 1395 ?
                <div className="col-lg-2 col-md-2 col-xl-2 menu-side">
                    <h3 className="text-right logo-text">Initex timing</h3>
                    <h6 className="text-right logo-text">v1.0</h6>
                    <div>
                        {userMenu.map((item) =>
                            <li key={item.key} className="navLinks"><Link className="navLinks"
                                                                          to={item.value}>{item.key}</Link></li>
                        )}
                    </div>
                </div>
                :
                <div className="col-xs-12 col-sm-12 col-md-12 background row">
                    <Dropdown isOpen={dropdownOpen} toggle={toggle}>
                        <DropdownToggle caret className='menuButton'>
                            Menu
                        </DropdownToggle>
                        <DropdownMenu style={{backgroundColor: "rgba(256, 256, 256, 0.9)"}}>
                            {userMenu.map((item, index) =>
                                <div key={index} onClick={toggle}><Link className="navLinks"
                                                                        to={item.value}>{item.key} </Link></div>)}
                        </DropdownMenu>
                    </Dropdown>
                    <div className="col-md-offset-3">
                        <h3 className="text-right logo-text">Initex timing</h3>
                        <h6 className="text-right logo-text">v1.0</h6>
                    </div>

                </div>
            }
        </>
    )
}

export default withRouter(NavMenu);