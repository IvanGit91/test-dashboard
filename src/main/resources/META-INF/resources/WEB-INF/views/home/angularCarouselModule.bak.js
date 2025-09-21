var AngularCarouselModule = angular.module('mainApp');

// main directive of our carousel
// $window is included so we can listen to window.resize (responsiveness)
// $timeout is included so we can run code to getting the carousel width after the dom is ready
AngularCarouselModule.directive('angularCarousel', function ($window, $timeout) {
    return {
        restrict: 'E',
        replace: true,
        scope: {cards: '='},
        controller: function ($scope) {

            // data tracked by the carousel
            $scope.localData = {
                cardListEl: null,   // dom reference to the card-list div so we can change it's margin-top
                carouselWidth: 0,   // real-time width of the carousel div
                carouselHeight: 0,  // fixed height of the carousel div
                minCardWidth: 140,  // min card width (also enforced in css)
                cardWidth: 140,       // width of a single carousel card (all card widths should match)
                maxCardsPerRow: 5,  // how many cards are currently visible on the carousel's 'row'
                dotList: [],        // the array of dots used to navigate the carousel
                activeDotIndex: 0   // array index of the 'active' dot
            };

            $scope.opzioni =
                {
                    carouselWidth: 1000,   // real-time width of the carousel div
                    carouselHeight: 0,  // fixed height of the carousel div
                    minCardWidth: 250,  // min card width (also enforced in css)
                    cardWidth: 250,       // width of a single carousel card (all card widths should match)
                    maxCardsPerRow: 5  // how many cards are currently visible on the carousel's 'row'
                }
            ;

            $scope.localState = {
                dotListChanged: false   // set to true when updateDotList is called, indicating the dot list should be rerendered
            };

            // rebuild the dot array based on the new max cards per row
            // reset the active dot to the first dot any time this is called
            $scope.updateDotList = function () {
                console.log("COUNT my options: " + $scope.localData.maxCardsPerRow);
                $scope.localData.maxCardsPerRow = $scope.calculateCardsPerRow();
                // console.log("COUNT my opzioni: " + $scope.opzioni.maxCardsPerRow);
                // console.log("COUNT local card: " + $scope.localData.maxCardsPerRow);
                // console.log("COUNT local card: " + $scope.localData[0].maxCardsPerRow);
                var dotCount = Math.ceil($scope.cards.length / $scope.localData.maxCardsPerRow);
                console.log("COUNT: " + dotCount);
                $scope.localData.dotList = new Array();
                for (var i = 0; i < dotCount; i++) {
                    $scope.localData.dotList.push({});
                }
                $scope.localState.dotListChanged = true;
                $scope.localData.activeDotIndex = 0;
            };

            // determine the current number of cards that can fit on a single row
            // based on the carousel's overall width & card width
            $scope.calculateCardsPerRow = function () {
                if ($scope.localData.cardWidth < $scope.localData.minCardWidth) {
                    $scope.localData.cardWidth = $scope.localData.minCardWidth;
                }
                var offsetCardWidth = $scope.localData.cardWidth;
                var maxCardsPerRow = Math.floor($scope.localData.carouselWidth / offsetCardWidth);
                if (maxCardsPerRow < 1) maxCardsPerRow = 1;
                return maxCardsPerRow;
            };

            // called on window.resize
            // the dot list is only updated if the max cards per row is different from the last calculation
            $scope.recalculate = function () {
                var oldMax = $scope.localData.maxCardsPerRow;
                console.log($scope.localData);
                //console.log("COUNT: " + $scope.opzioni);
                //console.log($scope.opzioni[0].lota);
                $scope.localData.maxCardsPerRow = $scope.calculateCardsPerRow();
                if (oldMax === $scope.localData.maxCardsPerRow) return;
                $scope.updateDotList();
                $scope.$apply();
            };

            // returns either the current dot list or a brand new one based on if the dot list has changed
            // since the last render
            $scope.getDotList = function () {
                if ($scope.localState.dotListChanged === true) {
                    $scope.localState.dotListChanged = false;
                    return $scope.localData.dotList.slice();
                } else {
                    return $scope.localData.dotList;
                }
            };

            // watch for changes to the active dot index (changes when a new dot is clicked)
            // calculate the new margin-top of the card-list based on the new value
            $scope.$watch(function () {
                return $scope.localData.activeDotIndex;
            }, function (newVal, oldVal) {
                var newMarginTop = ($scope.localData.activeDotIndex * $scope.localData.carouselHeight);
                $scope.localData.cardListEl.css('margin-top', [-newMarginTop, 'px'].join(''));
            });
        },
        template: [
            '<div class="angular-carousel">',
            '<div class="card-list flex-row centered-all wrap" style="margin-top: 0;">',
            '<carousel-card ng-repeat="card in cards track by $index"',
            ' card="card" index="$index" widthreference="localData.cardWidth">',
            '</carousel-card>',
            '</div>',
            '<div class="dot-list flex-row centered-all">',
            '<carousel-dot ng-repeat="dot in getDotList() track by $index"',
            ' index="$index"dot="dot" activedotindex="localData.activeDotIndex">',
            '</carousel-dot>',
            '</div>',
            '</div>'
        ].join(''),
        link: function (scope, element, attrs) {
            // get a dom reference to the card list so we can manipulate it's margin-top
            scope.localData.cardListEl = angular.element(element[0].querySelector('.card-list'));
            console.log(element[0]);
            // use angular's $timeout to wait until the dom is rendered before storing the carousel's dimensions
            $timeout(function () {
                scope.localData.carouselWidth = element[0].offsetWidth;
                scope.localData.carouselHeight = element[0].offsetHeight;
                scope.localData.cardWidth = angular.element(element[0].querySelector('.carousel-card')).width();
                console.log(scope.localData.cardWidth);
                // first time calculation of the dot list
                scope.updateDotList();

                // listen to window.resize and trigger a recalculation if the carousel's width changes
                angular.element($window).bind('resize', function () {
                    var newCarouselWidth = element[0].offsetWidth;
                    if (scope.localData.carouselWidth === newCarouselWidth) return;
                    scope.localData.carouselWidth = newCarouselWidth;
                    scope.recalculate();
                });
            });
        }
    };
});

// directive for a single carousel card
// each carousel card knows it's array index in the overall cards list,
// it's card data (just a title string in this example),
// and has a reference to the main angularCarousel directive's localData.cardWidth (2-way data bound)
AngularCarouselModule.directive('carouselCard', function ($window) {
    return {
        restrict: 'E',
        replace: true,
        scope: {index: '=', card: '=', widthreference: '='},
        controller: function ($scope) {

        },
        template: [
            '<div class="carousel-card flex-column centered-all">',
            '<header>',
            '<div>{{card.title}}</div>',
            '</header>',
            '</div>'
        ].join(''),
        link: function (scope, element, attrs) {
            // we only need one card to keep track of it's width
            if (scope.index > 0) return;
            scope.widthreference = element[0].offsetWidth;
            // listen to window.resize and update widthreference when the card width changes
            angular.element($window).bind('resize', function () {
                var newWidth = element[0].offsetWidth;
                if (scope.widthreference === newWidth) return;
                scope.widthreference = newWidth;
            });
        }
    };
});

// directive for a single carousel dot
// similar to the carousel card it tracks it's index in the dot array,
// it's dot data (empty object in this example),
// and a 2-way data bound reference to the angularCarousel's localData.activeDotIndex
AngularCarouselModule.directive('carouselDot', function ($window) {
    return {
        restrict: 'E',
        replace: true,
        scope: {index: '=', dot: '=', activedotindex: '='},
        controller: function ($scope) {
            // update the activedotindex when a dot is clicked
            $scope.onDotClick = function ($event) {
                $scope.activedotindex = $scope.index;
            }
        },
        template: [
            '<div class="carousel-dot flex-row centered-all" ng-class="{active: activedotindex === index}" ng-click="onDotClick($event)">',
            '<div class="inner-dot">',
            '</div>',
            '</div>'
        ].join(''),
        link: function (scope, element, attrs) {
        }
    };
});