import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Hashtag from './hashtag';
import HashtagDetail from './hashtag-detail';
import HashtagUpdate from './hashtag-update';
import HashtagDeleteDialog from './hashtag-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HashtagDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HashtagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HashtagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HashtagDetail} />
      <ErrorBoundaryRoute path={match.url} component={Hashtag} />
    </Switch>
  </>
);

export default Routes;
