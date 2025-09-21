<link rel="stylesheet" href="views/reportDetails/reportDetails.css">

<div class="container subContainer" ng-controller="ReportDetailsCtrl">
    <div class="row" style="font-size: 15pt" ng-init="page.selected='tre'">
        <div class="col-*-12">
            {{title}}
        </div>
    </div>

    <div class="row">
        <div class="col-*-12">
            <div class="customContainer">


                <div class="dash-panel dash-panel-default"
                     ng-class="{'dash-panel-success': testInfo.status === 'PASS', 'dash-panel-danger': testInfo.status === 'FAIL'}">

                    <div class="dash-panel-heading">
                        <div class="col-xs-1 glyphicon glyphicon-arrow-left" ng-click="goPrevious()"></div>
                        <div class="col-xs-10 pull-left" style="text-align: center;">
                            <b>{{testInfo.status}}</b>
                        </div>
                        <div class="col-xs-1"></div>
                    </div>
                </div>

                <!-- TEST INFO -->
                <div class="dash-panel dash-panel-default">
                    <div class="dash-panel-heading">
                        <div class="col-xs-12 pull-left">
                            <b>TEST INFO</b>
                        </div>
                    </div>
                    <div class="dash-panel-body">
                        <div class="form-group col-md-3">
                            <label>Name</label>
                            <input class="form-control" ng-model="testInfo.name" disabled>
                        </div>
                        <div class="form-group col-md-1">
                            <label>Version</label>
                            <input class="form-control" ng-model="testInfo.version" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Target</label>
                            <input class="form-control" ng-model="testInfo.vfName" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Variant</label>
                            <input class="form-control" ng-model="testInfo.variant" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Revision</label>
                            <input class="form-control" ng-model="testInfo.revision" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Release</label>
                            <input class="form-control" ng-model="testInfo.release" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Launcher</label>
                            <input class="form-control" ng-model="testInfo.launcher" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>SUT</label>
                            <input class="form-control" ng-model="testInfo.sut" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Scheduling</label>
                            <input class="form-control" ng-model="testInfo.scheduling" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Launched in</label>
                            <input class="form-control" ng-model="testInfo.created" disabled>
                        </div>
                        <div class="form-group col-md-2">
                            <label>Duration [s]</label>
                            <input class="form-control" ng-model="testInfo.time" disabled>
                        </div>
                    </div>
                </div>

                <!-- TEST INITIALIZATION -->
                <div class="dash-panel dash-panel-default" ng-if="testDetails.feedbackTestInizialization !== null">
                    <div class="dash-panel-heading">
                        <div class="col-xs-12 pull-left">
                            <b>INITIALIZATION TEST</b>
                        </div>
                    </div>
                    <div class="dash-panel-body">
                        <div class="blockElement withoutStatus col-sm-12">
                            <div class="col-sm-1"><span class="glyphicon"
                                                        ng-class="{'glyphicon-ok-sign': testDetails.feedbackTestInizialization.data.status === true, 'glyphicon-remove-sign': testDetails.feedbackTestInizialization.data.status === false, 'glyphicon-minus-sign': testDetails.feedbackTestInizialization.data.status !== false && testDetails.feedbackTestInizialization.data.status !== true}"></span>
                            </div>
                            <div class="col-sm-10">{{testDetails.feedbackTestInizialization.message}}</div>
                            <div class="col-sm-1 payload">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- MAIN TEST -->
                <div class="dash-panel dash-panel-default">
                    <div class="dash-panel-heading"><b>MAIN TEST</b></div>
                    <div class="dash-panel-body">
                        <div class="blockElement withoutStatus col-sm-12 titlePar">
                            <div class="col-sm-1 plusMinus"></div>
                            <div class="col-sm-1 status"><b>Status</b></div>
                            <div class="col-sm-1 step"><b>Step</b></div>
                            <div class="col-sm-1 type"><b>Type</b></div>
                            <div class="col-sm-2 name"><b>Name</b></div>
                            <div class="col-sm-4 message"><b>Message</b></div>
                            <div class="col-sm-1 payload"><b>Detail/Download</b></div>
                        </div>
                        <div style="word-break: break-word"
                             ng-repeat="(keyTestRepeat, test) in testDetails.reportTestRepeatWrappers">
                            <div class="blockElement withoutStatus col-sm-12">
                                <div class="col-sm-1 plusMinus">
                                    <a><span class="glyphicon"
                                             ng-class="{'glyphicon-plus-sign': test.open === false, 'glyphicon-minus-sign': test.open === true}"
                                             ng-init="test.open = true" ng-click="test.open = !test.open"></span></a>
                                </div>
                                <div class="col-sm-1 status"></div>
                                <div class="col-sm-1 step">
                                    <b>\#{{keyTestRepeat}}</b>
                                </div>
                                <div class="col-sm-1 type">
                                    <b>Test</b>
                                </div>
                                <div class="col-sm-2 name">
                                    {{testDetails.name}}
                                </div>
                                <div class="col-sm-4 message"></div>
                                <div class="col-sm-1 payload"></div>
                            </div>
                            <div ng-repeat="(keyRoutine, routine) in test.routines | orderBy: 'id'"
                                 ng-if="test.open !== false">
                                <div class="blockElement withoutStatus col-sm-12">
                                    <div class="col-sm-1 plusMinus">
                                        <a><span class="glyphicon"
                                                 ng-class="{'glyphicon-plus-sign': routine.open === false, 'glyphicon-minus-sign': routine.open === true}"
                                                 ng-init="routine.open = true"
                                                 ng-click="routine.open = !routine.open"></span></a>
                                    </div>
                                    <div class="col-sm-1 status"></div>
                                    <div class="col-sm-1 step">
                                        <b>\#{{keyTestRepeat}}.{{keyRoutine}}</b>
                                    </div>
                                    <div class="col-sm-1 type">
                                        <b>Routine</b>
                                    </div>
                                    <div class="col-sm-2 name"></div>
                                    <div class="col-sm-4 message"></div>
                                    <div class="col-sm-1 payload"></div>
                                </div>
                                <div ng-repeat="(keyRoutineRepeat, repeat) in routine.repeatList"
                                     ng-if="routine.open !== false">
                                    <div class="blockElement col-sm-12">
                                        <div class="col-sm-1 plusMinus">
                                            <a><span class="glyphicon"
                                                     ng-class="{'glyphicon-plus-sign': repeat.open === false, 'glyphicon-minus-sign': repeat.open === true}"
                                                     ng-init="repeat.open = true"
                                                     ng-click="repeat.open = !repeat.open"></span></a>
                                        </div>
                                        <div class="col-sm-1 status">
                                            <span class="glyphicon"
                                                  ng-class="{'glyphicon-ok-sign': repeat.feedback.status === 'pass', 'glyphicon-remove-sign': repeat.feedback.status === 'fail', 'glyphicon-minus-sign': repeat.feedback.status !== 'fail' && repeat.feedback.status !== 'pass'}"></span>
                                        </div>
                                        <div class="col-sm-1 step">
                                            <b>\#{{keyTestRepeat}}.{{keyRoutine}}.{{keyRoutineRepeat}}</b>
                                        </div>
                                        <div class="col-sm-1 type">
                                            <b>Routine</b>
                                        </div>
                                        <div class="col-sm-2 name">
                                            {{routine.name}}
                                        </div>
                                        <div class="col-sm-4 message">{{repeat.feedback.message}}</div>
                                        <div class="col-sm-1 payload">

                                        </div>
                                    </div>
                                    <div ng-repeat="(keyKeyword, keyword) in repeat.keywords"
                                         ng-if="repeat.open !== false">
                                        <div class="blockElement col-sm-12">
                                            <div class="col-sm-1 plusMinus">
                                                <a><span class="glyphicon"
                                                         ng-class="{'glyphicon-plus-sign': keyword.open === false, 'glyphicon-minus-sign': keyword.open === true}"
                                                         ng-init="keyword.open = false"
                                                         ng-click="keyword.open = !keyword.open"></span></a>
                                            </div>
                                            <div class="col-sm-1 status">
                                                <span class="glyphicon"
                                                      ng-class="{'glyphicon-ok-sign': keyword.feedback.status === 'pass' && keyword.enabled !== true, 'glyphicon-remove-sign': keyword.feedback.status === 'fail' && keyword.enabled !== true, 'glyphicon-minus-sign': keyword.feedback.status !== 'fail' && keyword.feedback.status !== 'pass' && keyword.enabled !== true, 'glyphicon-ban-circle': keyword.enabled === true}"></span>
                                            </div>
                                            <div class="col-sm-1 step">
                                                <b>\#{{keyTestRepeat}}.{{keyRoutine}}.{{keyRoutineRepeat}}.{{keyKeyword}}</b>
                                            </div>
                                            <div class="col-sm-1 type">
                                                <b>Keyword</b>
                                            </div>
                                            <div class="col-sm-2 name">
                                                {{keyword.name}}
                                            </div>
                                            <div class="col-sm-4 message">{{keyword.feedback.message}}</div>
                                            <div class="col-sm-1 payload">

                                            </div>
                                        </div>
                                        <div ng-repeat="(keyScene, scene) in keyword.sceneList"
                                             ng-if="keyword.open !== false">
                                            <div class="blockElement col-sm-12">
                                                <div class="col-sm-1 plusMinus">
                                                    <a><span class="glyphicon"
                                                             ng-class="{'glyphicon-plus-sign': scene.open === false, 'glyphicon-minus-sign': scene.open === true}"
                                                             ng-init="scene.open = true"
                                                             ng-click="scene.open = !scene.open"></span></a>
                                                </div>
                                                <div class="col-sm-1 status">
                                                </div>
                                                <div class="col-sm-1 step">
                                                    <b>\#{{keyTestRepeat}}.{{keyRoutine}}.{{keyRoutineRepeat}}.{{keyKeyword}}.{{keyScene}}</b>
                                                </div>
                                                <div class="col-sm-1 type">
                                                    <b>Scene</b>
                                                </div>
                                                <div class="col-sm-2 name"></div>
                                                <div class="col-sm-4 message"></div>
                                                <div class="col-sm-1 payload"></div>
                                            </div>
                                            <div ng-repeat="(keySceneRepeat, sceneRepeat) in scene.repeatList"
                                                 ng-if="scene.open !== false">
                                                <div class="blockElement col-sm-12">
                                                    <div class="col-sm-1 plusMinus">
                                                        <a><span class="glyphicon"
                                                                 ng-class="{'glyphicon-plus-sign': sceneRepeat.open === false, 'glyphicon-minus-sign': sceneRepeat.open === true}"
                                                                 ng-init="sceneRepeat.open = true"
                                                                 ng-click="sceneRepeat.open = !sceneRepeat.open"></span></a>
                                                    </div>
                                                    <div class="col-sm-1 status">
                                                        <span class="glyphicon"
                                                              ng-class="{'glyphicon-ok-sign': sceneRepeat.feedback.status === 'pass', 'glyphicon-remove-sign': sceneRepeat.feedback.status === 'fail', 'glyphicon-minus-sign': sceneRepeat.feedback.status !== 'fail' && sceneRepeat.feedback.status !== 'pass'}"></span>
                                                    </div>
                                                    <div class="col-sm-1 step">
                                                        <b>\#{{keyTestRepeat}}.{{keyRoutine}}.{{keyRoutineRepeat}}.{{keyKeyword}}.{{keyScene}}.{{keySceneRepeat}}</b>
                                                    </div>
                                                    <div class="col-sm-1 type">
                                                        <b>Scene</b>
                                                    </div>
                                                    <div class="col-sm-2 name">
                                                        {{scene.name}}
                                                    </div>
                                                    <div class="col-sm-4 message">{{scene.feedback.message}}</div>
                                                    <div class="col-sm-1 payload">
                                                    </div>
                                                </div>
                                                <div ng-repeat="(keyAction, action) in sceneRepeat.actions"
                                                     ng-if="sceneRepeat.open !== false">
                                                    <div class="blockElement col-sm-12">
                                                        <div class="col-sm-1 plusMinus"></div>
                                                        <div class="col-sm-1 status">
                                                            <span class="glyphicon"
                                                                  ng-class="{'glyphicon-ok-sign': action.feedback.status === 'pass', 'glyphicon-remove-sign': action.feedback.status === 'fail', 'glyphicon-minus-sign': action.feedback.status !== 'fail' && action.feedback.status !== 'pass'}"></span>
                                                        </div>
                                                        <div class="col-sm-1 step">
                                                            <b>\#{{keyTestRepeat}}.{{keyRoutine}}.{{keyRoutineRepeat}}.{{keyKeyword}}.{{keyScene}}.{{keySceneRepeat}}.{{keyAction}}</b>
                                                        </div>
                                                        <div class="col-sm-1 type">
                                                            <b>Action</b>
                                                        </div>
                                                        <div class="col-sm-2 name">
                                                            {{action.name}}
                                                        </div>
                                                        <div class="col-sm-4 message">{{action.feedback.message}}</div>
                                                        <div class="col-sm-1 payload">

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- TEST END -->
                <div class="dash-panel dash-panel-default">
                    <div class="dash-panel-heading">
                        <div class="col-xs-12 pull-left">
                            <b>END TEST</b>
                        </div>
                    </div>
                    <div class="dash-panel-body">
                        <div class="blockElement withoutStatus col-sm-12">
                            <div class="col-sm-1"><span class="glyphicon"
                                                        ng-class="{'glyphicon-ok-sign': testDetails.feedbackEndTest.status === true, 'glyphicon-remove-sign': testDetails.feedbackEndTest.status === false, 'glyphicon-minus-sign': testDetails.feedbackEndTest.status !== false && testDetails.feedbackEndTest.status !== true}"></span>
                            </div>
                            <div class="col-sm-10">{{testDetails.feedbackEndTest.message}}</div>
                            <div class="col-sm-1 payload">

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>


</div>