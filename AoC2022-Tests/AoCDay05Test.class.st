Class {
	#name : 'AoCDay05Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay05Test >> setUp [
	super setUp.
	self day: '5'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay05 new.
]

{ #category : 'tests' }
AoCDay05Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 'CWMTGHBDW'.
]

{ #category : 'tests' }
AoCDay05Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 'CMZ'.
]

{ #category : 'tests' }
AoCDay05Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 'SSCGWJCRB'.
]

{ #category : 'tests' }
AoCDay05Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 'MCD'.
]
