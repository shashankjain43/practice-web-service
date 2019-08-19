/**
 * Created by shruti.aggarwal on 17/2/16.
 */

import {MainController} from './main.controller';
import {WebDevTecService} from '../components/webDevTec/webDevTec.service';

'use strict';
export default angular.module('app.main', [])
              .service('webDevTec', WebDevTecService )
              .controller('MainController', MainController)
              .name;
