import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/instagram-user">
      Instagram User
    </MenuItem>
    <MenuItem icon="asterisk" to="/post">
      Post
    </MenuItem>
    <MenuItem icon="asterisk" to="/like">
      Like
    </MenuItem>
    <MenuItem icon="asterisk" to="/hashtag">
      Hashtag
    </MenuItem>
    <MenuItem icon="asterisk" to="/follower-following">
      Follower Following
    </MenuItem>
    <MenuItem icon="asterisk" to="/comment">
      Comment
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
