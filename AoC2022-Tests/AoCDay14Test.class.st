Class {
	#name : 'AoCDay14Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay14Test >> setUp [
	super setUp.
	self day: '14'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay14 new.
]

{ #category : 'tests' }
AoCDay14Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 913.
]

{ #category : 'tests' }
AoCDay14Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 24.
]
