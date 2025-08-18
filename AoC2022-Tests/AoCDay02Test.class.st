Class {
	#name : 'AoCDay02Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay02Test >> setUp [
	super setUp.
	self day: '2'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay02 new.
]

{ #category : 'tests' }
AoCDay02Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 11386.
]

{ #category : 'tests' }
AoCDay02Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 15.
]
