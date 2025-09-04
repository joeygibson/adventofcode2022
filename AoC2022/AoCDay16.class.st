"
Day16 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay16',
	#superclass : 'AoCBase',
	#instVars : [
		'graph',
		'connections',
		'flowRates'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay16 >> calculateDistances [
	"Calculate shortest distances between all pairs of valves using Floyd-Warshall"

	| valveNames |
	valveNames := connections keys asArray.

	"Initialize graph with infinite distances"
	valveNames do: [ :from |
			graph at: from put: Dictionary new.
			valveNames do: [ :to |
					(graph at: from) at: to put: (from = to
							 ifTrue: [ 0 ]
							 ifFalse: [ 999 ]) ] ].

	"Set direct connections to distance 1"
	connections keysAndValuesDo: [ :valve :tunnels |
		tunnels do: [ :tunnel | (graph at: valve) at: tunnel put: 1 ] ].

	"Floyd-Warshall algorithm"
	valveNames do: [ :k |
			valveNames do: [ :i |
					valveNames do: [ :j |
							| newDist |
							newDist := ((graph at: i) at: k) + ((graph at: k) at: j).
							(graph at: i) at: j put: (((graph at: i) at: j) min: newDist) ] ] ]
]

{ #category : 'as yet unclassified' }
AoCDay16 >> createGraph: lines [

	| valveRegex |
	valveRegex := '([A-Z]{2})' asRegex.

	lines do: [ :line |
			| valves flowRate name |
			valves := valveRegex matchesIn: line.
			flowRate := line extractAllNumbers first.

			name := valves first.

			flowRates at: name put: flowRate.
			connections at: name put: valves allButFirst ]
]

{ #category : 'as yet unclassified' }
AoCDay16 >> dfsMaxPressure: currentValve timeRemaining: timeLeft openedValves: opened positiveFlowValves: positiveValves memo: memo [
	"Depth-first search with memoization to find maximum pressure release"

	| memoKey maxPressure |
	"Create memoization key"
	memoKey := currentValve , '-' , timeLeft asString , '-'
	           , (opened asSortedCollection joinUsing: ',').

	"Check memo table"
	memo at: memoKey ifPresent: [ :cachedResult | ^ cachedResult ].

	maxPressure := 0.

	"Try opening each unopened valve with positive flow"
	positiveValves do: [ :valveName |
			(opened includes: valveName) ifFalse: [
					| travelTime totalTime pressureGain remainingTime newOpened subResult |
					"Calculate time to reach and open this valve"
					travelTime := self distanceFrom: currentValve to: valveName.
					totalTime := travelTime + 1. "1 minute to open valve"

					"Check if we have enough time"
					totalTime < timeLeft ifTrue: [
							remainingTime := timeLeft - totalTime.
							pressureGain := (flowRates at: valveName) * remainingTime.
							newOpened := opened copy.
							newOpened add: valveName.

							"Recursively find max pressure from this state"
							subResult := self
								             dfsMaxPressure: valveName
								             timeRemaining: remainingTime
								             openedValves: newOpened
								             positiveFlowValves: positiveValves
								             memo: memo.

							maxPressure := maxPressure max: pressureGain + subResult ] ] ].

	"Memoize and return result"
	memo at: memoKey put: maxPressure.
	^ maxPressure
]

{ #category : 'configuration' }
AoCDay16 >> distanceFrom: from to: to [

	^ (graph at: from) at: to
]

{ #category : 'initialization' }
AoCDay16 >> initialize [
	super initialize.
	graph := Dictionary new.
	connections := Dictionary new.
	flowRates := Dictionary new
]

{ #category : 'as yet unclassified' }
AoCDay16 >> part1: lines [

	| positiveFlowValves memo |
	
	self createGraph: lines.
	self calculateDistances.
	
	positiveFlowValves := self valvesWithPositiveFlow.
	memo := Dictionary new.

	^ self
		  dfsMaxPressure: 'AA'
		  timeRemaining: 30
		  openedValves: Set new
		  positiveFlowValves: positiveFlowValves
		  memo: memo
]

{ #category : 'as yet unclassified' }
AoCDay16 >> valvesWithPositiveFlow [

	^ flowRates associations
		  select: [ :assoc | assoc value > 0 ]
		  thenCollect: [ :assoc | assoc key ]
]
