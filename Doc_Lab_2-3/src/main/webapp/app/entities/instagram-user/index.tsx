import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InstagramUser from './instagram-user';
import InstagramUserDetail from './instagram-user-detail';
import InstagramUserUpdate from './instagram-user-update';
import InstagramUserDeleteDialog from './instagram-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InstagramUserDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InstagramUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InstagramUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InstagramUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={InstagramUser} />
    </Switch>
  </>
);

export default Routes;
